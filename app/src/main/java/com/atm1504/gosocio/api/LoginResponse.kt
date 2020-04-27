package com.atm1504.gosocio.api

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    private val status: Int = 0,
    private val access_token: String = "",
    private val email: String = "",
    private val name: String = "",
    private val phone: String = "",
    val coins: Number = 0,
    val stick1: Number = 0,
    val stick2: Number = 0,
    val stick3: Number = 0,
    val stick4: Number = 0,
    val stick5: Number = 0

) {
    fun status(): Int {
        return status
    }

    fun access_token(): String {
        return access_token
    }

    fun email(): String {
        return email
    }

    fun name(): String {
        return name
    }

    fun phone(): String {
        return phone
    }

    fun coins(): Number {
        return coins
    }
}