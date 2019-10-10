package com.example.water13.source

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.water13.bean.User
import com.example.water13.component.SPF_FILE_NAME_USER
import com.squareup.moshi.Moshi
import retrofit2.HttpException

object Repo {

    private lateinit var spf: SharedPreferences

    private val moshi = Moshi.Builder().build()

    fun init(app: Application) {
        spf = app.getSharedPreferences(SPF_FILE_NAME_USER, Context.MODE_PRIVATE)
        if (spf.contains("username")) {
            User.instance = getUser()
        }
    }

    private fun saveUser(user: User) {
        spf.edit().putString("username", user.username)
            .putString("password", user.password)
            .putInt("id", user.id)
            .putString("token", user.token)
            .apply()
    }

    fun getUser(): User {
        return User(
            spf.getString("username", "")!!,
            spf.getString("password", "")!!,
            spf.getInt("id", -1),
            spf.getString("token", "")!!
        )
    }

    @Throws(Exception::class)
    suspend fun register(newUser: User) {
        try {
            Network.api.registerAsync(newUser.toDto()).await()
        } catch (e: HttpException) {
            throw Exception(e.response()?.errorBody()?.string())
        }
    }

    @Throws(Exception::class)
    suspend fun login(user: User) {
        try {
            val data =
                Network.api.loginAsync(user.toDto()).await().data
            user.id = data.user_id
            user.token = data.token
            User.instance = user
            saveUser(user)
        } catch (e: HttpException) {
            throw Exception(e.response()?.errorBody()?.string())
        }
    }

    @Throws(Exception::class)
    suspend fun logout(user: User) {
        try {
            Network.api.logoutAsync(user.token).await().data
            spf.edit().clear().apply()
            User.instance = User()
        } catch (e: HttpException) {
            throw Exception(e.response()?.errorBody()?.string())
        }
    }

    @Throws(Exception::class)
    suspend fun check(user: User) {
        try {
            val data = Network.api.checkAsync(user.token).await().data
            if (data.user_id == null) {
                throw Exception(data.result)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    @Throws(Exception::class)
    suspend fun open(user: User): OpenGameResponse.Data {
        try {
            return Network.api.openGameAsync(user.token).await().data
        } catch (e: Exception) {
            throw e
        }
    }
}