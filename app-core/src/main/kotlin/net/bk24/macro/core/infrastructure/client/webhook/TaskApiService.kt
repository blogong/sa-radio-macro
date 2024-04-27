package net.bk24.macro.core.infrastructure.client.webhook

import net.bk24.macro.common.ServerTodo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskApiService {
    @POST("task/execute")
    fun executeTask(@Body todo: ServerTodo): Call<Void>
}
