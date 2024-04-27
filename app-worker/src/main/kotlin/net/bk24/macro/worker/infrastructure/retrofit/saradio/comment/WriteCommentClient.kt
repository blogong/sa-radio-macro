package net.bk24.macro.worker.infrastructure.retrofit.saradio.comment

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import net.bk24.macro.common.RetryInterceptor
import net.bk24.macro.worker.infrastructure.retrofit.saradio.helper.ReadHeader
import okhttp3.Authenticator
import okhttp3.ConnectionPool
import okhttp3.Credentials
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.springframework.http.HttpHeaders
import retrofit2.Retrofit
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit

object WriteCommentClient {
    private const val BASE_URL = "https://saradioapi.nexon.com"

    private val headers = ReadHeader.fromYml()

    val proxyAddress = InetSocketAddress("proxy.proxy-cheap.com", 31112)
    val proxy = Proxy(Proxy.Type.HTTP, proxyAddress)
    val proxyAuthenticator = Authenticator { _, response ->
        val credential = Credentials.basic("ftukdh3x", "TtOK1y9VqwcQWuKK")
        response.request.newBuilder()
            .header("Proxy-Authorization", credential)
            .build()
    }

    val dispatcher = Dispatcher().apply {
        maxRequests = 6 // 최대 동시 요청 수
        maxRequestsPerHost = 6 // 호스트 당 최대 요청 수
    }

    private val okHttpClient = OkHttpClient.Builder()
        .proxy(proxy)
        .readTimeout(30, TimeUnit.MINUTES)
        .writeTimeout(30, TimeUnit.MINUTES)
        .addInterceptor(RetryInterceptor(1))
        .connectionPool(ConnectionPool(6, 6, TimeUnit.MINUTES)) // 최대 5개의 유휴 연결을 5분 동안 유지
        .proxyAuthenticator(proxyAuthenticator)
        .dispatcher(dispatcher)
        .followRedirects(false) // 리다이렉션 비활성화
        .followSslRedirects(false) // SSL 리다이렉션 비활성화
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(HttpHeaders.ACCEPT, headers.accept)
                .addHeader(HttpHeaders.CONNECTION, headers.connection)
                .addHeader(HttpHeaders.ACCEPT_ENCODING, headers.acceptEncoding)
                .addHeader(HttpHeaders.CONTENT_TYPE, headers.contentType)
                .addHeader(HttpHeaders.USER_AGENT, headers.userAgent)
                .addHeader(HttpHeaders.HOST, headers.host)
                .addHeader(HttpHeaders.ACCEPT_LANGUAGE, headers.acceptLanguage)
                .addHeader(HttpHeaders.COOKIE, headers.cookie)
                .addHeader(HttpHeaders.CONTENT_LENGTH, "162")
                .build()
            chain.proceed(request)
        }
        .readTimeout(50, TimeUnit.MINUTES) // 읽기 타임아웃: 50분
        .writeTimeout(30, TimeUnit.SECONDS) // 쓰기 타임아웃: 30초
        .build()

    val writeComment: WriteCommentApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/x-www-form-urlencoded".toMediaType()))
            .build()

        retrofit.create(WriteCommentApiService::class.java)
    }
}
