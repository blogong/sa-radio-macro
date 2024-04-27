package net.bk24.macro.core.infrastructure.client.webhook

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import net.bk24.macro.common.ServerTodo
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object TaskProvideClient {

    private val jsonConverterFactory = Json.asConverterFactory("application/json".toMediaType())

    fun execute(todo: ServerTodo, ipAddress: String) {
        val todoApiService = createTodoApiService(ipAddress)
        try {
            val response = todoApiService.executeTask(todo).execute()
            if (response.isSuccessful) {
                println("Task executed successfully.")
            } else {
                println("Failed to execute task: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory)
            .build()
    }

    private fun createTodoApiService(ipAddress: String): TaskApiService {
        val retrofit = createRetrofit("http://$ipAddress:8080")
        return retrofit.create(TaskApiService::class.java)
    }
}
