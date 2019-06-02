package hr.ferit.brunozoric.taskie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 class Task(

    @PrimaryKey(autoGenerate = true)
    var taskDbId : Long? = null,
    var BackendId: String? = null,
    val title: String,
    val description: String,
    val priority: Priority
)