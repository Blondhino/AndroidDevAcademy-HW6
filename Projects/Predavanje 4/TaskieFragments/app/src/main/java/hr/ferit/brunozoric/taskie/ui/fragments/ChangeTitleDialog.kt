package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepo
import kotlinx.android.synthetic.main.fragment_change_title_dialog.*

class ChangeTitleDialog :DialogFragment() {

    private val repository = TaskRoomRepo()
    lateinit var task: Task
    private var titleChangedListener : TitleChangedListener?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_title_dialog, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        cancelButtonDialogTitle.setOnClickListener { dismiss() }
        okButtonDialogTitle.setOnClickListener { setNewTitle() }
    }

    private fun setNewTitle() {
        repository.changeTitle(task,newTitle.text.toString())
        titleChangedListener?.onTitleChanged()
        dismiss()
    }

    companion object {
        fun newInstance(): ChangeTitleDialog {
            return ChangeTitleDialog()
        }
    }

    fun setTitleChangedListener (listener : TitleChangedListener){

        titleChangedListener =listener
    }

    interface TitleChangedListener {
        fun onTitleChanged()
    }

}