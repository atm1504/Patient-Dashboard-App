package com.atm1504.gosocio.api

import androidx.annotation.Keep

@Keep
data class PrescriptionResponse(
    private val name:String="",
    private val age:Number=0,
    private val symtomps:String="",
    private val medicine:String="",
    private val dose:String=""
)