package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
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
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractorImpl
import hr.ferit.brunozoric.taskie.prefs.TaskPrefs
import hr.ferit.brunozoric.taskie.prefs.TaskPrefs.KEY
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepo
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import kotlinx.android.synthetic.main.fragment_change_priority_dialog.*
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddTaskFragmentDialog: DialogFragment() {

    private var taskAddedListener: TaskAddedListener? = null
    private val repository = TaskRoomRepo()
    private val interactor = BackendFactory.getTaskieInteractor()

    interface TaskAddedListener{
        fun onTaskAdded()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }
    fun setTaskAddedListener(listener: TaskAddedListener){
        taskAddedListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_new_task, container)
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

    private fun initUi(){
        context?.let {
            prioritySelector.adapter = ArrayAdapter<Priority>(it, android.R.layout.simple_spinner_dropdown_item, Priority.values())
            when (lastPriority()){
                "LOW" -> prioritySelector.setSelection(0)
                "MEDIUM" -> prioritySelector.setSelection(1)
                "HIGH" -> prioritySelector.setSelection(2)
            }
        }
    }

    private fun lastPriority(): String {
        return TaskPrefs.getString(KEY,"LOW")!!

    }

    private fun initListeners(){
        saveTaskAction.setOnClickListener{ saveTask() }
    }

    private fun saveTask() {
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val priority = prioritySelector.selectedItem as Priority
        interactor.save(AddTaskRequest(title,description,priority.getIntKey()), getSaveCallback())

        rememberLastPriority()
        clearUi()


        dismiss()
    }

    private fun rememberLastPriority() {
        TaskPrefs.store(KEY, prioritySelector.selectedItem.toString())
    }

    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
        prioritySelector.setSelection(0)
    }

    private fun isInputEmpty(): Boolean = isEmpty(newTaskTitleInput.text) || isEmpty(newTaskDescriptionInput.text)

    companion object{
        fun newInstance(): AddTaskFragmentDialog {
            return AddTaskFragmentDialog()
        }
    }


    private fun getSaveCallback(): Callback<BackendTask> = object  : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {

        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            Log.e("TASK :",response.body().toString())
            taskAddedListener?.onTaskAdded()
        }

    }

}