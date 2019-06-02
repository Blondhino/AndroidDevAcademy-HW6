package hr.ferit.brunozoric.taskie.ui.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.service.autofill.SaveCallback
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.common.*
import hr.ferit.brunozoric.taskie.model.BackendFactory
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.model.response.DeleteTaskResponse
import hr.ferit.brunozoric.taskie.model.response.GetTasksResponse
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepo
import hr.ferit.brunozoric.taskie.prefs.TaskPrefs
import hr.ferit.brunozoric.taskie.ui.activities.ContainerActivity
import hr.ferit.brunozoric.taskie.ui.activities.MainActivity
import hr.ferit.brunozoric.taskie.ui.activities.SplashActivity
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.file.Files.delete

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener {




    override fun onTaskAdded() {
        refreshTasks()

    }

    //private val repository = TaskRoomRepo()
    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }
    private val interactor = BackendFactory.getTaskieInteractor()




    override fun getLayoutResourceId() = R.layout.fragment_tasks
    override fun onStart() {
        super.onStart()
        refreshTasks()

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        refreshTasks()
        setHasOptionsMenu(true)

    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
        initSwipeRecyclerView()
    }

    private fun initSwipeRecyclerView() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                interactor.delete(adapter.getTaskBy(viewHolder.adapterPosition), deleteCallback())
            }




        }



        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
    }

    private fun deleteCallback(): Callback<DeleteTaskResponse>  = object : Callback<DeleteTaskResponse>{
        override fun onFailure(call: Call<DeleteTaskResponse>, t: Throwable) {
            Toast.makeText(Taskie.getAppContext(),"Error: Delete Error", Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call<DeleteTaskResponse>, response: Response<DeleteTaskResponse>) {
            Toast.makeText(Taskie.getAppContext(),"Task deleted", Toast.LENGTH_SHORT).show()
            refreshTasks()
        }


    }


    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tolbar_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.navDeleteAllTasks -> {removeAllTasks(); refreshTasks()}
            R.id.navLogOut -> {TaskPrefs.clearUserToken(); val intent = Intent (activity, SplashActivity::class.java);
                activity!!.startActivity(intent)

            }
          R.id.navRefresh -> {refreshTasks()}
        }

        return super.onOptionsItemSelected(item)
    }

    private fun removeAllTasks() {
        for (i in 0 until adapter.itemCount){
            interactor.delete(adapter.getTaskBy(i), deleteCallback())
        }
    }


    private fun onItemSelected(task: BackendTask){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.id)
        }
        startActivity(detailsIntent)
    }

    private fun refreshTasks() {
        progress.visible()
        interactor.getTasks(getTaskieCallback())
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun getTaskieCallback(): Callback<GetTasksResponse> = object : Callback<GetTasksResponse> {
        override fun onFailure(call: Call<GetTasksResponse>?, t: Throwable?) {
            progress.gone()
            //TODO : handle default error
        }

        override fun onResponse(call: Call<GetTasksResponse>?, response: Response<GetTasksResponse>) {
            progress.gone()
            noData.gone()
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response)
                    else -> handleSomethingWentWrong()
                }
            }
        }
    }

    private fun handleOkResponse(response: Response<GetTasksResponse>) {
        response.body()?.notes?.run {
            checkList(this)
            adapter.setData(this)
        }
    }

    private fun handleSomethingWentWrong() = this.activity?.displayToast("Something went wrong!")

    private fun checkList(notes: MutableList<BackendTask>) {
        if (notes.isEmpty()) {
            noData.visible()
        } else {
            noData.gone()
        }
    }




    companion object {
        fun newInstance(): Fragment {
            return TasksFragment()
        }
    }







}