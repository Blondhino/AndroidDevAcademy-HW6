package hr.ferit.brunozoric.taskie.persistence

import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.db.DaoProvider
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

class TaskRoomRepo : RepoInterface {


    private var db = DaoProvider.getInstance(Taskie.getAppContext())
    private var taskDao = db.taskieDao()


    override fun sortByPriority() :List<Task> {
       return taskDao.sortByPriority()
    }


    override fun changeTitle(task: Task, title: String) {
        taskDao.updateTitle(task.taskDbId!!,title)
    }


    override fun changeDescription(task: Task, description: String) {
        taskDao.updateDescription(task.taskDbId!!,description)
    }


    override fun getAllTasks(): List<Task> {
        return taskDao.loadAll()
    }

    override fun getTask(id: Long): Task {
        return taskDao.getTaskBy(id)
    }

    override fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    override fun removeTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override fun removeAllTasks() {
        taskDao.deleteAllTasks()
    }

    override fun changeTaskPriority(task: Task, priority: Priority) {
        taskDao.updateTaskPriority(task.taskDbId!!,priority)
    }
}


 interface RepoInterface{

     fun getAllTasks() : List <Task>
     fun getTask(id:Long) : Task
     fun addTask(task :Task)
     fun removeTask(task :Task)
     fun removeAllTasks()
     fun changeTaskPriority(task: Task, priority: Priority)
     fun changeTitle(task: Task,title:String)
     fun changeDescription (task: Task, description: String)
     fun sortByPriority(): List<Task>
 }