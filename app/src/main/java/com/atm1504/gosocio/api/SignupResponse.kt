package com.atm1504.gosocio.api

import androidx.annotation.Keep

@Keep
data class SignupResponse(
    private val status: Int = 0,
    private val message: String = ""
) {
    fun status(): Int {
        return status
    }

    fun message(): String {
        return message
    }

}