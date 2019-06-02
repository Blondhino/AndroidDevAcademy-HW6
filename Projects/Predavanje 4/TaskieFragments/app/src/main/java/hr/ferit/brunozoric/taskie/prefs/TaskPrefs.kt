package hr.ferit.brunozoric.taskie.prefs

import android.preference.PreferenceManager
import hr.ferit.brunozoric.taskie.Taskie

object TaskPrefs : TaskPrefsHelper {

    const val KEY = "SAVING_KEY"
    const val PREFERENCES_NAME = "taskie_prefs"
    const val KEY_USER_TOKEN = "user_token"

    private fun sharedPrefs () = PreferenceManager.getDefaultSharedPreferences(Taskie.getAppContext())
    private val preferences = Taskie.instance.providePreferences()


    fun store(key: String , value: String) {
        val editor = sharedPrefs().edit()
        editor.putString(key , value).apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPrefs().getString(key,defaultValue)
    }

    override fun getUserToken(): String = preferences.getString(KEY_USER_TOKEN, "")
    override fun storeUserToken(token: String) = preferences.edit().putString(
        KEY_USER_TOKEN, token).apply()
    override fun clearUserToken() = preferences.edit().remove(KEY_USER_TOKEN).apply()


}


 fun provideSharedPrefs() : TaskPrefsHelper {
    return TaskPrefs
}

