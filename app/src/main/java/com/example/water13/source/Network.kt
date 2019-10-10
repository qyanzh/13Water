package com.example.water13.source

import com.example.water13.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    /* 注册 */
    @POST("auth/register")
    fun registerAsync(@Body user: UserDto): Deferred<RegisterResponse>

    /* 登录 */
    @POST("auth/login")
    fun loginAsync(@Body user: UserDto): Deferred<LoginResponse>

    /* 注销 */
    @POST("auth/logout")
    fun logoutAsync(@Header("X-Auth-Token") token: String): Deferred<LogoutResponse>

    /* 验证 */
    @GET("auth/validate")
    fun checkAsync(@Header("X-Auth-Token") token: String): Deferred<CheckResponse>

    /* 开局 */
    @POST("game/open")
    fun openGameAsync(@Header("X-Auth-Token") token: String): Deferred<OpenGameResponse>

}

object Network {

    private val clientBuilder = OkHttpClient.Builder()

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(this)
        }
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.shisanshui.rtxux.xyz/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(clientBuilder.build())
        .build()
    val api = retrofit.create(Api::class.java)
}


data class UserDto(
    val username: String,
    val password: String
)


data class RegisterResponse(
    val `data`: Data,
    val status: Int
) {
    data class Data(
        val msg: String,
        val user_id: Int
    )
}

data class LoginResponse(
    val `data`: Data,
    val status: Int
) {
    data class Data(
        val token: String,
        val user_id: Int
    )
}

data class LogoutResponse(
    val `data`: Data,
    val status: Int
) {
    data class Data(
        val result: String
    )
}

data class CheckResponse(
    val `data`: Data,
    val status: Int
) {
    class Data {
        var result: String? = null
        var user_id: Int? = null
    }
}

data class OpenGameResponse(
    val `data`: Data,
    val status: Int
) {
    data class Data(
        val card: String,
        val id: Int
    )
}
