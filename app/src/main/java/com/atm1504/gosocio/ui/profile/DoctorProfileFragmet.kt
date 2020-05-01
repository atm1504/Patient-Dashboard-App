package com.atm1504.gosocio.ui.profile

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.atm1504.gosocio.R
import com.atm1504.gosocio.utils.utils
import kotlinx.android.synthetic.main.fragment_doctor_profile.*
import kotlinx.android.synthetic.main.fragment_home.*

class DoctorProfileFragmet : Fragment() {

    private lateinit var doctorProfileViewModel: DoctorProfileViewModel
    private val PREFS_NAME = "atm"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        doctorProfileViewModel =
            ViewModelProviders.of(this).get(DoctorProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_doctor_profile, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Doctor Profile"
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContents()
    }

    fun setContents(){

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        doctor_show_name.text=sharedPref.getString("name","atm")
        doctor_show_email.text=sharedPref.getString("email","me@atm1504.in@gmail.com")
        doctor_show_phone.text=sharedPref.getString("phone","7234634237")
        show_days.text=sharedPref.getString("days","Monday - Friday")
        show_time.text=sharedPref.getString("time","09:00 AM - 11:00 AM , 03:00 PM - 06:00 PM")
        show_venue.text=sharedPref.getString("venue"," Amt Hospital , Room - RA112")
    }


}