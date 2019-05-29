package hr.ferit.brunozoric.taskie.db

import androidx.room.TypeConverter
import hr.ferit.brunozoric.taskie.model.Priority

class TypeConverter {

    companion object{

        @TypeConverter
        @JvmStatic
        fun fromPriority(priority : Priority): Int {
            return priority.getIntKey()
        }


        @TypeConverter
        @JvmStatic
        fun toPriority(key: Int) : Priority{
            return when (key){
                0 ->Priority.LOW
                1 ->Priority.MEDIUM
                2 ->Priority.HIGH
              else ->Priority.HIGH
            }

        }

    }




}