package net.bk24.macro.worker.infrastructure.retrofit.saradio.post

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import net.bk24.macro.worker.infrastructure.retrofit.saradio.helper.ReadHeader
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.springframework.http.HttpHeaders
import retrofit2.Retrofit
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit

object WritePostClient {
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

    private val okHttpClient = OkHttpClient
        .Builder()
        .readTimeout(30, TimeUnit.MINUTES)
        .writeTimeout(30, TimeUnit.MINUTES)
        .proxy(proxy)
        .proxyAuthenticator(proxyAuthenticator)
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
                .addHeader(HttpHeaders.CONTENT_LENGTH, "147")
                .build()
            chain.proceed(request)
        }
        .build()

    val writePost: WritePostApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/x-www-form-urlencoded".toMediaType()))
            .client(okHttpClient)
            .build()

        retrofit.create(WritePostApiService::class.java)
    }
}
