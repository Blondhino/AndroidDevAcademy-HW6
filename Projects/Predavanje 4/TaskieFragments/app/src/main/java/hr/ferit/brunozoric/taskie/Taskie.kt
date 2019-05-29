package hr.ferit.brunozoric.taskie

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import hr.ferit.brunozoric.taskie.Taskie.Companion.instance

class Taskie: Application() {



    companion object {
        lateinit var instance: Taskie
            private set
        fun getAppContext(): Context= instance.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }



}