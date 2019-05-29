package hr.ferit.brunozoric.taskie.model

import androidx.annotation.ColorRes
import hr.ferit.brunozoric.taskie.R

enum class Priority(@ColorRes private val colorRes: Int,  private val intKey : Int) {
    LOW(R.color.colorLow,0),
    MEDIUM(R.color.colorMedium,1),
    HIGH(R.color.colorHigh,2);

    fun getColor(): Int = colorRes
    fun getIntKey() : Int = intKey
}

