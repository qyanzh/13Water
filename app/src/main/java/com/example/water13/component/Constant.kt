package com.example.water13.component

import org.json.JSONObject
import retrofit2.HttpException

const val SPF_FILE_NAME_USER = "user"

const val STATUS_SUCCESS = 0
const val STATUS_REGISTERED = 1001
const val STATUS_AUTH_USED = 1002
const val STATUS_AUTH_FAIL = 1003
const val STATUS_TOKEN_EXPIRED = 1004
const val STATUS_NOT_MATCH = 1005
const val STATUS_TOO_MANY = 2001
const val STATUS_CHEAT = 2002
const val STATUS_ILLEGAL = 2003
const val STATUS_GAME_NOT_EXIST = 3001
const val STATUS_USER_NOT_EXIST = 3002

const val NETWORK_ERROR_STRING = "网络错误"

private fun getStatusMsg(status: Int): String = when (status) {
    STATUS_SUCCESS -> "成功"
    STATUS_REGISTERED -> "用户名已被使用"
    STATUS_AUTH_USED -> "学号已绑定"
    STATUS_AUTH_FAIL -> "教务处认证失败"
    STATUS_TOKEN_EXPIRED -> "Token过期"
    STATUS_NOT_MATCH -> "账号密码不匹配"
    STATUS_TOO_MANY -> "未结束战局过多"
    STATUS_CHEAT -> "出千"
    STATUS_ILLEGAL -> "不合法墩牌"
    STATUS_GAME_NOT_EXIST -> "战局不存在或已结束"
    STATUS_USER_NOT_EXIST -> "玩家不存在"
    else -> "未知错误"
}

fun getStatusMsg(e: HttpException): String =
    getStatusMsg(JSONObject(e.response()?.errorBody()?.string()!!).getInt("status"))