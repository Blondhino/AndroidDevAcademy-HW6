package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepo
import kotlinx.android.synthetic.main.fragment_change_priority_dialog.*
import kotlinx.android.synthetic.main.fragment_task_details.*

class ChangePriorityDialog : DialogFragment()
{
    private val repository = TaskRoomRepo()
    lateinit var task: Task
    private var priorityChangedListener : PriorityChangedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_priority_dialog, container)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners(task)
    }

    private fun setListeners(task: Task) {
        newPriorityLow.setOnClickListener {setNewPriority(task,Priority.LOW) }
        newPriorityMedium.setOnClickListener {setNewPriority(task,Priority.MEDIUM) }
        newPriorityHigh.setOnClickListener { setNewPriority(task,Priority.HIGH) }

    }

    private fun setNewPriority(task: Task, priority: Priority) {
        repository.changeTaskPriority(task,priority)
        priorityChangedListener?.onPriorityChanged()
        dismiss()

    }
fun setPriorityChangedListener(listener : PriorityChangedListener){
    priorityChangedListener=listener
}

    companion object {
        fun newInstance(): ChangePriorityDialog {
            return ChangePriorityDialog()
        }

    }
    interface PriorityChangedListener {
        fun onPriorityChanged()
    }
}