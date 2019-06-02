package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.BackendFactory
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.model.response.GetTaskResponse
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepo
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class TaskDetailsFragment : BaseFragment(), UpdateTaskFragmentDialog.UpdateListenr {


    private var taskID:String = String()
    private var interactor = BackendFactory.getTaskieInteractor()


    override fun onTaskUpdatedListener() {
      tryDisplayTask(taskID)
    }


    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        setListeners()




    }

    private fun setListeners() {
     editTaskButton.setOnClickListener { startUpdateDialog() }
    }

    private fun startUpdateDialog() {
        val dialog = UpdateTaskFragmentDialog.newInstance()
        dialog.setTaskUpdatedListener(this)
        dialog.taskId =taskID
        dialog.show(childFragmentManager, dialog.tag)
    }





    private fun tryDisplayTask(id: String) {
        try {
            interactor.getTaskById(id,getTaskByIdCallBack())

        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun getTaskByIdCallBack(): retrofit2.Callback<BackendTask> = object  : retrofit2.Callback<BackendTask>{
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            detailsTaskTitle.text = response.body()?.title
            detailsTaskDescription.text=response.body()?.content
           detailsPriorityView.setBackgroundResource(when(response.body()?.taskPriority)
           {
               0 -> {Priority.LOW.getColor()}
               1 -> {Priority.MEDIUM.getColor()}
               2 -> {Priority.HIGH.getColor()}
                else -> {Priority.LOW.getColor()}
           }


            )
        }

    }




    companion object {

        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
