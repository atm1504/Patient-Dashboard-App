package com.atm1504.gosocio.api

import androidx.annotation.Keep

@Keep
class ReportResponse(
    val id: String = "",
    val complain: String = "",
    val road_name: String = "",
    val current_status: String = "",
    val coins: Number = 0,
    val progress: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val status: Number = 0,
    val image: String = ""
)