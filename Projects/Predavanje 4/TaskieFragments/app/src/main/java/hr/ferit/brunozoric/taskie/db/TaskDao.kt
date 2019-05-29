package hr.ferit.brunozoric.taskie.db

import androidx.appcompat.widget.DialogTitle
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task


@Dao
interface TaskDao {

    @Query("SELECT * FROM Task" )
    fun loadAll(): List<Task>


    @Query("SELECT * FROM Task WHERE taskDbId = :Id" )
    fun getTaskBy(Id: Long): Task


    @Insert(onConflict = IGNORE)
    fun insertTask (task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE  from Task")
    fun deleteAllTasks()

    @Query("UPDATE Task SET priority= :taskPriority WHERE taskDbId= :taskId")
    fun updateTaskPriority(taskId: Long, taskPriority: Priority)

    @Query("UPDATE Task SET title= :taskTitle WHERE taskDbId= :taskId")
    fun updateTitle(taskId: Long, taskTitle: String)

    @Query ("UPDATE Task SET description= :taskDescription WHERE taskDbId= :taskId")
    fun updateDescription(taskId: Long, taskDescription : String)

    @Query ("SELECT * FROM Task ORDER BY priority DESC")
    fun sortByPriority() : List<Task>

}