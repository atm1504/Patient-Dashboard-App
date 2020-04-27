package com.atm1504.gosocio.api

import androidx.annotation.Keep

@Keep
data class ReportedTasksResponse(
    val status: Int = 0,
    val reports: List<Report> = ArrayList()
)
