package com.atm1504.gosocio.ui.Prescription

class Prescription {
    var symptoms: String? = null
    var medicine: String? = null
    var dose: String? = null
    var date: String? = null


    constructor() {}
    constructor(
        symptoms: String?,
        medicine: String?,
        dose: String?,
        date: String?

    ) {
        this.symptoms = symptoms
        this.medicine = medicine
        this.dose = dose
        this.date = date

    }

    fun getsymptoms(): String? {
        return symptoms
    }

    fun setsymptoms(symptoms: String) {
        this.symptoms = symptoms
    }

    fun getmedicine(): String? {
        return medicine
    }

    fun setmedicine(medicine: String) {
        this.medicine = medicine
    }



    fun getdate(): String? {
        return date
    }

    fun setdate(date: String?) {
        this.date = date
    }

    fun getdose(): String? {
        return dose
    }

    fun setdose(dose: String) {
        this.dose = dose
    }



}
