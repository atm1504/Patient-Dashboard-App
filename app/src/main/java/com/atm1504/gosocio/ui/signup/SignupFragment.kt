package com.atm1504.gosocio.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import android.widget.*

import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.RetrofitApi
import com.atm1504.gosocio.api.SignupResponse
import kotlinx.android.synthetic.main.fragment_signup.*

import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class SignupFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var signupViewModel: SignupViewModel
    private lateinit var spinner_specialist: Spinner
    private lateinit var specialization_doctor: String
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var phone: EditText
    private lateinit var btnNext: Button
    private lateinit var usertype: RadioGroup
    private lateinit var doctor: RadioButton
    private lateinit var patient: RadioButton
    private lateinit var special_linear: LinearLayout
    private lateinit var time: EditText
    private lateinit var days: EditText
    private lateinit var venue: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_signup, container, false)

        val specialization = resources.getStringArray(R.array.doctor_specialist)

        //access the spinner
        spinner_specialist = rootview.findViewById(R.id.spinner_specialist)


        name = rootview.findViewById(R.id.name)
        email = rootview.findViewById(R.id.email)
        phone = rootview.findViewById(R.id.phone)
        password = rootview.findViewById(R.id.password)
        btnNext = rootview.findViewById(R.id.next)

        special_linear = rootview.findViewById(R.id.specialist_linear)

        usertype = rootview.findViewById(R.id.usertype)
        doctor = rootview.findViewById(R.id.doctor)
        patient = rootview.findViewById(R.id.patient)
        patient.setChecked(true)
        days=rootview.findViewById(R.id.days_signup)
        time=rootview.findViewById(R.id.time_signup)
        venue=rootview.findViewById(R.id.venue_signup)

        if (spinner_specialist != null) {
            val adapter =
                activity?.let {
                    ArrayAdapter(
                        it,
                        android.R.layout.simple_spinner_dropdown_item,
                        specialization
                    )
                }
            spinner_specialist.adapter = adapter

            spinner_specialist.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    specialization_doctor = p0?.getItemAtPosition(p2).toString()
                    //Toast.makeText(context, specialization_doctor, Toast.LENGTH_LONG).show()
                }
            }
        }

        return rootview;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNext.setOnClickListener {
            val id: Int
            id = usertype.checkedRadioButtonId
            if (id == doctor.id) {
                var err: Int
                err = 0
                val name = name.text?.trim().toString()
                val email = email.text?.trim().toString()
                val phone = phone.text?.trim().toString()
                val password = password.text?.trim().toString()
                val venue:String = venue.text?.trim().toString()
                val days:String = days.text?.trim().toString()
                val time:String = time.text?.trim().toString()

                if (name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || phone?.length != 10) {
                    Toast.makeText(context, "Enter proper datas int he field", Toast.LENGTH_SHORT)
                        .show()
                    err += 1
                }

                if (err == 0) {
                    signUpDoctor(
                        name,
                        email,
                        phone,
                        password,
                        specialization_doctor,
                        days,
                        venue,
                        time
                    )
                }

            } else {
                var err: Int
                err = 0
                val name = name.text?.trim().toString()
                val email = email.text?.trim().toString()
                val phone = phone.text?.trim().toString()
                val password = password.text?.trim().toString()
                val venue:String = venue.text?.trim().toString()
                val days:String = days.text?.trim().toString()
                val time:String = time.text?.trim().toString()

                if (name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || phone?.length != 10) {
                    Toast.makeText(context, "Enter proper datas int he field", Toast.LENGTH_SHORT)
                        .show()
                    err += 1
                }


                if (err == 0) {
                    signUpPatient(
                        name,
                        email,
                        phone,
                        password,
                        days,
                        time,
                        venue
                    )
                }
            }

        }

        usertype.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, i ->
                if (i == R.id.doctor) {

                    btnNext.text = "Next"
                    special_linear.visibility = VISIBLE
                    venue.visibility= VISIBLE
                    days.visibility= VISIBLE
                    time.visibility= VISIBLE

                }
                if (i == R.id.patient) {

                    btnNext.text = "Submit"
                    special_linear.visibility = GONE
                    venue.visibility= GONE
                    days.visibility= GONE
                    time.visibility= GONE
                }


            }
        )

    }

    private fun signUpDoctor(

        name: String,
        email: String,
        phone: String,
        password: String,
        specialization: String,
        days: String,
        venue:String,
        time:String
    ) {
        Toast.makeText(context,"$name $email $password $days $time $venue  $phone  $specialization",Toast.LENGTH_LONG).show()
        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(MediaType.parse("text/plain"), email)
        val name = RequestBody.create(MediaType.parse("text/plain"), name)
        val days = RequestBody.create(MediaType.parse("text/plain"), days)
        val time = RequestBody.create(MediaType.parse("text/plain"), time)
        val venue = RequestBody.create(MediaType.parse("text/plain"), venue)

        val password = RequestBody.create(MediaType.parse("text/plain"), password)
        val phone = RequestBody.create(MediaType.parse("text/plain"), phone)
        val specialization = RequestBody.create(MediaType.parse("text/plain"), specialization)




        val call = retofitApi.signupDoctor(email, password, name, phone, days, venue, time, specialization)
        call.enqueue(object : Callback<SignupResponse> {
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun signUpPatient(
        name: String,
        email: String,
        phone: String,
        venue: String,
        time: String,
        days: String,
        password: String
    ) {
        Toast.makeText(context,"$name $email $password $days $time $venue  $phone ",Toast.LENGTH_LONG).show()
        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(MediaType.parse("text/plain"), email)
        val name = RequestBody.create(MediaType.parse("text/plain"), name)
        val password = RequestBody.create(MediaType.parse("text/plain"), password)
        val days = RequestBody.create(MediaType.parse("text/plain"), days)
        val time = RequestBody.create(MediaType.parse("text/plain"), time)
        val venue = RequestBody.create(MediaType.parse("text/plain"), venue)
        val phone = RequestBody.create(MediaType.parse("text/plain"), phone)

        val call = retofitApi.signupPatient(email, password, name, phone, days, venue, time)
        call.enqueue(object : Callback<SignupResponse> {
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }


}






