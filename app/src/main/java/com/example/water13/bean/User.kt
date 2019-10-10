package com.example.water13.bean

import android.os.Parcelable
import com.example.water13.source.UserDto
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var username: String = "",
    var password: String = "",
    var id: Int = -1,
    var token: String = ""
) :
    Parcelable {
    companion object {
        @Transient
        var instance = User()
    }

    fun toDto() = UserDto(username, password)

}