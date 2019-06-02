package hr.ferit.brunozoric.taskie.networking.interactors

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.model.request.UpdateTaskRequest
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.*
import hr.ferit.brunozoric.taskie.networking.TaskieApiService
import retrofit2.Callback

class TaskieInteractorImpl (private val apiService : TaskieApiService) : TaskieInteractor {
    override fun updateTask(request: UpdateTaskRequest, updateCallback: Callback<UpdateResponse>) {
        apiService.updateTask(request).enqueue(updateCallback)
    }


    override fun getTaskById(id: String, taskieResponseCallback: Callback<BackendTask>) {
        apiService.getTaskById(id).enqueue(taskieResponseCallback)
    }

    override fun delete(task: BackendTask, deleteCallback: Callback<DeleteTaskResponse>) {
        apiService.deleteTask(task.id).enqueue(deleteCallback)
    }


    override fun getTasks(taskieResponseCallback: Callback<GetTasksResponse>) {
        apiService.getTasks().enqueue(taskieResponseCallback)
    }

    override fun register(request: UserDataRequest, registerCallback: Callback<RegisterResponse>) {
     apiService.register(request).enqueue(registerCallback)
    }

    override fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>) {
        apiService.login(request).enqueue(loginCallback)
    }

    override fun save(request: AddTaskRequest, saveCallback: Callback<BackendTask>) {
        apiService.save(request).enqueue(saveCallback)
    }
}