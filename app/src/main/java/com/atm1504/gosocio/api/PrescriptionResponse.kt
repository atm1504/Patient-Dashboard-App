package com.atm1504.gosocio.api

import androidx.annotation.Keep
import com.atm1504.gosocio.ui.Prescription.Prescription

@Keep
data class PrescriptionResponse( private val symptoms : String = "",
        private val medicine : String = "",
        private val dose : String = "",
        private val date : String = "") {
    val reports: List<Prescription> = ArrayList()
   fun symptoms () : String {
       return symptoms
   }
    fun medicine () : String {
        return medicine
    }
    fun dose () : String {
        return dose
    }
    fun date () : String {
        return date
    }
}