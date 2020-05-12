package com.atm1504.gosocio.api

import androidx.annotation.Keep
import com.atm1504.gosocio.ui.Prescription.Prescription

@Keep
data class PrescriptionResponse(
    val reports: List<Prescription> = ArrayList()
)

