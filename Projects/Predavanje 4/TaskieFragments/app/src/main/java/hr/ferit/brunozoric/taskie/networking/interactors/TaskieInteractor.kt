package hr.ferit.brunozoric.taskie.networking.interactors

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.model.request.UpdateTaskRequest
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.*
import retrofit2.Callback

interface TaskieInteractor {

    fun getTasks (taskieResponseCallback : Callback <GetTasksResponse>)
    fun register (request: UserDataRequest, registerCallback: Callback<RegisterResponse>)
    fun login (request: UserDataRequest,loginCallback: Callback<LoginResponse>)
    fun save (request: AddTaskRequest,saveCallback: Callback<BackendTask>)
    fun delete (task : BackendTask,  deleteCallback: Callback<DeleteTaskResponse>)
    fun getTaskById(id: String, taskieResponseCallback: Callback<BackendTask>)
    fun updateTask(request: UpdateTaskRequest, updateCallback: Callback<UpdateResponse> )
}