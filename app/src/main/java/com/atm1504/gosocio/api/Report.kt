package com.atm1504.gosocio.api

data class Report(
    val id: String = "",
    val complain: String = "",
    val road_name: String = "",
    val current_status: String = "",
    val coins: Number = 0
)