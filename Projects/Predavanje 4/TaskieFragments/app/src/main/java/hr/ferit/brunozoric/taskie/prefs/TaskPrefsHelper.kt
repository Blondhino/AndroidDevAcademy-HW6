package hr.ferit.brunozoric.taskie.prefs

interface TaskPrefsHelper {

    fun getUserToken(): String

    fun storeUserToken(token: String)

    fun clearUserToken()

}