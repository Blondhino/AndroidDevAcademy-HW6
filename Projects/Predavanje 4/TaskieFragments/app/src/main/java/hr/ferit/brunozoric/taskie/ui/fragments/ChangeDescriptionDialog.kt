package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepo
import kotlinx.android.synthetic.main.fragment_change_description_dialog.*
import kotlinx.android.synthetic.main.fragment_change_priority_dialog.view.*

class ChangeDescriptionDialog :DialogFragment() {

    private val repository = TaskRoomRepo()
    lateinit var task: Task
    private var descriptionChangedListener : DescriptionChangedListener?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_description_dialog, container)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        okButtonDialogDescription.setOnClickListener { setNewDescription() }
        cancelButtonDialogDescription.setOnClickListener { dismiss() }
    }

    private fun setNewDescription() {
        repository.changeDescription(task,newDescription.text.toString())
        descriptionChangedListener?.onDescriptionChanged()
        dismiss()
    }

    companion object {
        fun newInstance(): ChangeDescriptionDialog {
            return ChangeDescriptionDialog()
        }

    }

    fun setDescriptionChangedListener(listener : DescriptionChangedListener){
        descriptionChangedListener=listener
    }


    interface DescriptionChangedListener{
        fun onDescriptionChanged()
    }

}