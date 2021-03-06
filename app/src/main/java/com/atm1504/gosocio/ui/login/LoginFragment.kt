package com.atm1504.gosocio.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.LoginResponse
import com.atm1504.gosocio.api.RetrofitApi
import com.atm1504.gosocio.utils.utils
import kotlinx.android.synthetic.main.fragment_login.*

import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private val PREFS_NAME = "atm"
    private lateinit var doctor:RadioButton
    public lateinit var patient: RadioButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        doctor=root.findViewById(R.id.login_doctor)
        patient =root.findViewById(R.id.login_patient)
        patient.setChecked(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            val email = login_email.text?.trim().toString()
            val password = login_password.text?.trim().toString()
            val err = 0
            if (doctor.isChecked){
                if (email.isNullOrBlank() || password.isNullOrBlank()) {
                    Toast.makeText(context, "Enter all the fields correctly", Toast.LENGTH_LONG).show()
                } else {
                    loginDoctor(email, password)
//                    val doctorDashboardFragment:DoctorDashboardFragment = DoctorDashboardFragment();
//                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment,doctorDashboardFragment,"newfragment")
//                        ?.addToBackStack(null)?.commit()

                }
            }
            if (patient.isChecked){
                if (email.isNullOrBlank() || password.isNullOrBlank()) {
                    Toast.makeText(context, "Enter all the fields correctly", Toast.LENGTH_LONG).show()
                } else {
                    loginPatient(email, password)
                }
            }





        }
    }

    private fun loginDoctor(email: String, password: String) {
        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(MediaType.parse("text/plain"), email)
        val password = RequestBody.create(MediaType.parse("text/plain"), password)

        val call = retofitApi.loginDoctor(email, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.body()?.status() == 200) {
                    val sharedPref: SharedPreferences = requireContext().getSharedPreferences(
                        PREFS_NAME,
                        Context.MODE_PRIVATE
                    )
                    val body = response.body()
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString("name", body?.name())
                    editor.putString("email", body?.email())
                    editor.putString("phone", body?.phone())
                    editor.putString("isDoctor","Yes")
                    editor.putString("days",body?.days())
                    editor.putString("time",body?.time())
                    editor.putString("venue",body?.venue())
                    editor.putString("access_token", body!!.access_token())
                    editor.putBoolean("loggedIn",true)
                    editor.commit()
                }else{
                    utils.showToast(requireContext(),"Something went wrong try aga")
                }
            }

        })
    }
    private fun loginPatient(email: String, password: String) {
        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(MediaType.parse("text/plain"), email)
        val password = RequestBody.create(MediaType.parse("text/plain"), password)

        val call = retofitApi.loginPatient(email, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.body()?.status() == 200) {
                    val sharedPref: SharedPreferences = requireContext().getSharedPreferences(
                        PREFS_NAME,
                        Context.MODE_PRIVATE
                    )
                    val body = response.body()
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString("name", body?.name())
                    editor.putString("email", body?.email())
                    editor.putString("phone", body?.phone())
                    editor.putString("isDoctor","No")
                    editor.putString("access_token", body!!.access_token())
                    editor.putBoolean("loggedIn",true)
                    editor.commit()
                }else{
                    utils.showToast(requireContext(),"Something went wrong try aga")
                }
            }

        })
    }
}