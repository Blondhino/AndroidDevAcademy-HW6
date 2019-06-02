package hr.ferit.brunozoric.taskie.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepo

class TaskAdapter(private val onItemSelected: (BackendTask) -> Unit) : Adapter<TaskHolder>() {

    private val data: MutableList<BackendTask> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskHolder(v)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bindData(data[position], onItemSelected)

    }

   // fun sortTaskByPriority(){
   //     data.sortByDescending { it.priority.getIntKey()}
   //     notifyDataSetChanged()
   //  }

    fun setData(data: MutableList<BackendTask>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun getTaskBy(position :Int) : BackendTask{
        return data [position]
    }
}





