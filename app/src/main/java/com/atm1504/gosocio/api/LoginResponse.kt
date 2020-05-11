package com.atm1504.gosocio.api

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    private val status: Int = 0,
    private val access_token: String = "",
    private val email: String = "",
    private val name: String = "",
    private val phone: String = "",
    private val days: String = "",
    private val time: String = "",
    private val venue: String = ""

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

    fun days(): String{
        return days
    }

    fun time(): String{
        return time
    }

    fun venue(): String{
        return  venue
    }
}