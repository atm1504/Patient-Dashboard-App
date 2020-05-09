package com.atm1504.gosocio.api

import androidx.annotation.Keep

@Keep
data class PrescriptionResponse(
    private val name:String="",
    private val age:Number=0,
    private val symtomps:String="",
    private val medicine:String="",
    private val dose:String=""
){
    fun name():String{
        return name
    }

    fun age():Number{
        return age
    }
    fun symptomps() :String{
        return symtomps
    }
    fun medicine() :String{
        return medicine
    }
    fun dose():String{
        return dose
    }

}