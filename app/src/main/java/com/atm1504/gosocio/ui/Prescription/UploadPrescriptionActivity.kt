package com.atm1504.gosocio.ui.Prescription

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.LoginResponse
import com.atm1504.gosocio.api.PrescriptionResponse
import com.atm1504.gosocio.api.RetrofitApi
import kotlinx.android.synthetic.main.activity_upload_prescription.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext
import java.util.*

class UploadPrescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val id = pres_patient_id.text.trim().toString()
        val symptoms = pres_symptoms.text.trim().toString()
        val medicine = pres_medicine.text.trim().toString()
        val dose = pres_dose.text.trim().toString()
        var date: String = ""
        pres_date.setOnClickListener( View.OnClickListener {
            val calendar = Calendar.getInstance()
            val mYear = calendar[Calendar.YEAR]
            val mMonth = calendar[Calendar.MONTH]
            val mdayofMonth = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog =
                DatePickerDialog(this,
                    OnDateSetListener { view, year, month, dayOfMonth ->
                       date =  String.format("%02d-%02d-%02d", year, month + 1, dayOfMonth)

                    }, mYear, mMonth, mdayofMonth
                )      
            datePickerDialog.setTitle("Choose Date")
            datePickerDialog.show()
        })
        pres_date.text =date

        uploadPrescription(symptoms, medicine, dose, date, id)



    }

    private fun uploadPrescription(

        symptoms: String,
        medicine: String,
        dose : String,
        date: String,
    userId:String
    ) {
        Toast.makeText(this,"$symptoms $medicine $dose $date",
            Toast.LENGTH_LONG).show()
        val retofitApi = RetrofitApi.create()
        val symptoms = RequestBody.create(MediaType.parse("text/plain"), symptoms)
        val medicine = RequestBody.create(MediaType.parse("text/plain"), medicine)
        val dose = RequestBody.create(MediaType.parse("text/plain"), dose)
        val date = RequestBody.create(MediaType.parse("text/plain"), date)




        val call = retofitApi.uploadPrescriptionbyId(symptoms, medicine, dose, date, userId)
        call.enqueue(object :Callback<PrescriptionResponse>{
            override fun onFailure(call: Call<PrescriptionResponse>, t: Throwable) {
                Toast.makeText(this@UploadPrescriptionActivity,"Something unexpected happened",
                    Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<PrescriptionResponse>,
                response: Response<PrescriptionResponse>
            ) {
                TODO("Not yet implemented")
            }
        })
    }

}