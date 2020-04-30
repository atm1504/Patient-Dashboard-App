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
    private lateinit var address: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var phone: EditText
    private lateinit var pincode: EditText
    private lateinit var aadhar: EditText
    private lateinit var btnNext: Button
    private lateinit var usertype: RadioGroup
    private lateinit var gender: RadioGroup
    private lateinit var doctor: RadioButton
    private lateinit var patient: RadioButton
    private lateinit var male: RadioButton
    private lateinit var female: RadioButton
    private lateinit var special_linear: LinearLayout


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
        address = rootview.findViewById(R.id.address)
        phone = rootview.findViewById(R.id.phone)
        pincode = rootview.findViewById(R.id.pin)
        password = rootview.findViewById(R.id.password)
        confirmPassword = rootview.findViewById(R.id.confirmPassword)
        btnNext = rootview.findViewById(R.id.next)
        aadhar = rootview.findViewById(R.id.aadhar)

        special_linear = rootview.findViewById(R.id.specialist_linear)

        usertype = rootview.findViewById(R.id.usertype)
        gender = rootview.findViewById(R.id.gender)
        doctor = rootview.findViewById(R.id.doctor)
        patient = rootview.findViewById(R.id.patient)
        male = rootview.findViewById(R.id.male)
        female = rootview.findViewById(R.id.female)
        doctor.setChecked(true)
        male.setChecked(true)

        special_linear.visibility = VISIBLE

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
                val address = address.text?.trim().toString()
                val phone = phone.text?.trim().toString()
                val pin = pin.text?.trim().toString()
                val password = password.text?.trim().toString()
                val aadhar_num = aadhar.text?.trim().toString()
                val confirm_password = confirmPassword.text?.trim().toString()
                val checkgender: String
                if (this.gender.checkedRadioButtonId == R.id.male)
                    checkgender = "Male"
                else
                    checkgender = "Female"

                if (name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || phone?.length != 10) {
                    Toast.makeText(context, "Enter proper datas int he field", Toast.LENGTH_SHORT)
                        .show()
                    err += 1
                }

                if (password != confirm_password) {
                    Toast.makeText(context, "EPasswords didn't match", Toast.LENGTH_SHORT).show()
                    err += 1
                }

                if (aadhar_num.length != 12) {
                    Toast.makeText(context, "Enter valid addhar number", Toast.LENGTH_SHORT).show()
                    err += 1
                }

                if (err == 0) {
                    signUpDoctor(
                        name,
                        email,
                        phone,
                        checkgender,
                        address,
                        pin,
                        password,
                        confirm_password,
                        aadhar_num,
                        specialization_doctor
                    )
                }

            } else {
                var err: Int
                err = 0
                val name = name.text?.trim().toString()
                val email = email.text?.trim().toString()
                val address = address.text?.trim().toString()
                val phone = phone.text?.trim().toString()
                val pin = pin.text?.trim().toString()
                val password = password.text?.trim().toString()
                val aadhar_num = aadhar.text?.trim().toString()
                val confirm_password = confirmPassword.text?.trim().toString()
                val checkgender: String
                if (this.gender.checkedRadioButtonId == R.id.male)
                    checkgender = "Male"
                else
                    checkgender = "Female"

                if (name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || phone?.length != 10) {
                    Toast.makeText(context, "Enter proper datas int he field", Toast.LENGTH_SHORT)
                        .show()
                    err += 1
                }

                if (password != confirm_password) {
                    Toast.makeText(context, "EPasswords didn't match", Toast.LENGTH_SHORT).show()
                    err += 1
                }



                if (err == 0) {
                    signUpPatient(
                        name,
                        email,
                        phone,
                        checkgender,
                        address,
                        pin,
                        password,
                        confirm_password,
                        aadhar_num
                    )
                }
            }

        }

        usertype.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, i ->
                if (i == R.id.doctor) {

                    btnNext.text = "Next"
                    special_linear.visibility = VISIBLE

                }
                if (i == R.id.patient) {

                    btnNext.text = "Submit"
                    special_linear.visibility = GONE
                }


            }
        )

    }

    private fun signUpDoctor(

        name: String,
        email: String,
        phone: String,
        gender: String,
        address: String,
        pin: String,
        password: String,
        confirmPassword: String,
        aadharNum: String,
        specialization: String
    ) {
        Toast.makeText(context,"$name $email $password $gender $address $pin $confirmPassword $phone $aadharNum $specialization",Toast.LENGTH_LONG).show()
        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(MediaType.parse("text/plain"), email)
        val name = RequestBody.create(MediaType.parse("text/plain"), name)
        val gender = RequestBody.create(MediaType.parse("text/plain"), gender)
        val address = RequestBody.create(MediaType.parse("text/plain"), address)
        val pin = RequestBody.create(MediaType.parse("text/plain"), pin)

        val password = RequestBody.create(MediaType.parse("text/plain"), password)
        val confirm_password = RequestBody.create(MediaType.parse("text/plain"), confirmPassword)
        val phone = RequestBody.create(MediaType.parse("text/plain"), phone)
        val aadhar = RequestBody.create(MediaType.parse("text/plain"), aadharNum)
        val specialization = RequestBody.create(MediaType.parse("text/plain"), specialization)




        val call = retofitApi.signupDoctor(email, password, name, phone, confirm_password, aadhar, gender, address, pin, specialization)
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
        gender: String,
        address: String,
        pin: String,
        password: String,
        confirmPassword: String,
        aadharNum: String
    ) {
        Toast.makeText(context,"$name $email $password $gender $address $pin $confirmPassword $phone $aadharNum",Toast.LENGTH_LONG).show()
        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(MediaType.parse("text/plain"), email)
        val name = RequestBody.create(MediaType.parse("text/plain"), name)
        val password = RequestBody.create(MediaType.parse("text/plain"), password)
        val gender = RequestBody.create(MediaType.parse("text/plain"), gender)
        val address = RequestBody.create(MediaType.parse("text/plain"), address)
        val pin = RequestBody.create(MediaType.parse("text/plain"), pin)
        val confirm_password = RequestBody.create(MediaType.parse("text/plain"), confirmPassword)
        val phone = RequestBody.create(MediaType.parse("text/plain"), phone)
        val aadhar = RequestBody.create(MediaType.parse("text/plain"), aadharNum)


        val call = retofitApi.signupPatient(email, password, name, phone, confirm_password, aadhar, gender, address, pin)
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






