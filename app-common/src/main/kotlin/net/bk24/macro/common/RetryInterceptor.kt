package net.bk24.macro.common

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(private val maxRetries: Int = 3) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null
        var retryCount = 0

        while (response == null && retryCount < maxRetries) {
            try {
                response = chain.proceed(chain.request())
            } catch (e: IOException) {
                retryCount++
                if (retryCount == maxRetries) {
                    throw e // 최대 시도 횟수에 도달하면 예외를 던짐
                }
            }
        }

        return response!!
    }
}
