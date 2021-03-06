package com.atm1504.gosocio.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.atm1504.gosocio.R
import kotlinx.android.synthetic.main.fragment_patient_profile.*

class PatientProfileFragment : Fragment() {

    private lateinit var patientProfileViewModel: PatientProfileViewModel
    private val PREFS_NAME = "atm"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        patientProfileViewModel =
            ViewModelProviders.of(this).get(PatientProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_patient_profile, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Patient Profile"
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContents()
    }
    fun setContents(){
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        show_name.text=sharedPref.getString("name","atm")
        show_email.text=sharedPref.getString("email","me@atm1504.in@gmail.com")
        show_phone.text=sharedPref.getString("phone","7234634237")
    }
}