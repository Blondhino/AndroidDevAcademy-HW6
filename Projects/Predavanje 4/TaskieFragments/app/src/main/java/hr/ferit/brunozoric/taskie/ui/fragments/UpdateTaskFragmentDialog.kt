package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.BackendFactory
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.request.UpdateTaskRequest
import hr.ferit.brunozoric.taskie.model.response.UpdateResponse
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepo
import hr.ferit.brunozoric.taskie.prefs.TaskPrefs
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import kotlinx.android.synthetic.main.fragment_dialog_new_task.saveTaskAction
import kotlinx.android.synthetic.main.fragment_dialog_update_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateTaskFragmentDialog :DialogFragment() {


    private val repository = TaskRoomRepo()
    private val interactor = BackendFactory.getTaskieInteractor()
    private var taskUpdatedListener :UpdateListenr? = null
     var taskId : String? = null


    interface TaskAddedListener{
        fun onTaskAdded()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_update_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()

    }


    fun setTaskUpdatedListener (listener : UpdateListenr){
        taskUpdatedListener=listener
    }

    private fun initUi(){
        context?.let {
            updateTaskPrioritySelector.adapter = ArrayAdapter<Priority>(it, android.R.layout.simple_spinner_dropdown_item, Priority.values())
            when (lastPriority()){
                "LOW" -> updateTaskPrioritySelector.setSelection(0)
                "MEDIUM" -> updateTaskPrioritySelector.setSelection(1)
                "HIGH" -> updateTaskPrioritySelector.setSelection(2)
            }
        }
    }

    private fun lastPriority(): String {
        return TaskPrefs.getString(TaskPrefs.KEY,"LOW")!!

    }

    private fun initListeners(){
        saveTaskAction.setOnClickListener{ saveTask() }
    }

    private fun saveTask() {

        val priority = updateTaskPrioritySelector.selectedItem as Priority
        interactor.updateTask(UpdateTaskRequest(taskId!!, updateTaskTitleInput.text.toString(),
            updateTaskDescriptionInput.text.toString(),priority.getIntKey()),updateCallback())


    }

    private fun updateCallback(): Callback<UpdateResponse> = object : Callback<UpdateResponse>{
        override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {

        }

        override fun onResponse(call: Call<UpdateResponse>, response: Response<UpdateResponse>) {
            taskUpdatedListener?.onTaskUpdatedListener()
            dismiss()
        }

    }







    companion object{
        fun newInstance():UpdateTaskFragmentDialog {
            return UpdateTaskFragmentDialog()
        }
    }

    interface UpdateListenr{
        fun onTaskUpdatedListener()
    }

}