package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.View
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepo
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*

class TaskDetailsFragment : BaseFragment(), ChangePriorityDialog.PriorityChangedListener, ChangeTitleDialog.TitleChangedListener, ChangeDescriptionDialog.DescriptionChangedListener {
    override fun onDescriptionChanged() {
         detailsTaskDescription.text= repository.getTask(taskID).description
    }

    override fun onTitleChanged() {
        detailsTaskTitle.text = repository.getTask(taskID).title
    }

    override fun onPriorityChanged() {
        detailsPriorityView.setBackgroundResource(repository.getTask(taskID).priority.getColor())
    }

    private val repository = TaskRoomRepo()
    private var taskID:Long = NO_TASK


    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLong(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        setListeners()




    }

    private fun setListeners() {
        detailsPriorityView.setOnClickListener { startChangePriorityDialog(repository.getTask(taskID)) }
        detailsTaskTitle.setOnClickListener { startChangeTitleDialog(repository.getTask(taskID)) }
        detailsTaskDescription.setOnClickListener { startChangeDescriptionDialog(repository.getTask(taskID)) }
    }

    private fun startChangeDescriptionDialog(task: Task) {
        var dialog = ChangeDescriptionDialog.newInstance()
        dialog.task=task
        dialog.setDescriptionChangedListener(this)
        dialog.show(childFragmentManager,dialog.tag)
    }

    private fun startChangeTitleDialog(task :Task) {
        var dialog = ChangeTitleDialog.newInstance()
        dialog.task=task
        dialog.setTitleChangedListener(this)
        dialog.show(childFragmentManager,dialog.tag)
    }


    private fun tryDisplayTask(id: Long) {
        try {
            val task = repository.getTask(id)
            displayTask(task)
        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun displayTask(task: Task) {
        detailsTaskTitle.text = task.title
        detailsTaskDescription.text = task.description
        detailsPriorityView.setBackgroundResource(task.priority.getColor())
    }
    private fun startChangePriorityDialog(task :Task) {
        var dialog = ChangePriorityDialog.newInstance()
        dialog.task=task
        dialog.setPriorityChangedListener(this)
        dialog.show(childFragmentManager, dialog.tag)

    }



    companion object {
        const val NO_TASK :Long = -1

        fun newInstance(taskId: Long): TaskDetailsFragment {
            val bundle = Bundle().apply { putLong(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
