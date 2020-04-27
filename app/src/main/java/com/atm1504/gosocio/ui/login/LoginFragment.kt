package com.atm1504.gosocio.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.LoginResponse
import com.atm1504.gosocio.api.RetrofitApi
import com.atm1504.gosocio.api.SignupResponse
import com.atm1504.gosocio.utils.utils
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_signup.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private val PREFS_NAME = "atm"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            val email = login_email.text?.trim().toString()
            val password = login_password.text?.trim().toString()
            val err = 0

            if (email.isNullOrBlank() || password.isNullOrBlank()) {
                Toast.makeText(context, "Enter allt he fields correctly", Toast.LENGTH_LONG).show()
            } else {
                loginUser(email, password)
            }

        }
    }

    private fun loginUser(email: String, password: String) {
        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(MediaType.parse("text/plain"), email)
        val password = RequestBody.create(MediaType.parse("text/plain"), password)

        val call = retofitApi.login(email, password)
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
                    editor.putFloat("coins", body!!.coins().toFloat())
                    editor.putInt("stick1", body.stick1.toInt())
                    editor.putInt("stick2", body.stick2.toInt())
                    editor.putInt("stick3", body.stick3.toInt())
                    editor.putInt("stick4", body.stick4.toInt())
                    editor.putInt("stick5", body.stick5.toInt())
                    editor.putString("access_token", body.access_token())
                    editor.putBoolean("loggedIn",true)
                    editor.commit()
                }else{
                    utils.showToast(requireContext(),"Something went wrong try aga")
                }
            }

        })
    }
}