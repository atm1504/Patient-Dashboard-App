package com.atm1504.gosocio.ui.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.atm1504.gosocio.R
import com.atm1504.gosocio.ui.home.HomeViewModel

/**
 * A simple [Fragment] subclass.
 */
class DoctorDashboardFragment : Fragment() {
    private lateinit var doctorDashboardViewModel: DoctorDashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        doctorDashboardViewModel =
            ViewModelProviders.of(this).get(DoctorDashboardViewModel::class.java)
        // Inflate the layout for this fragment
        val rootview =  inflater.inflate(R.layout.fragment_doctor_dashboard, container, false)
        return rootview
    }

}
