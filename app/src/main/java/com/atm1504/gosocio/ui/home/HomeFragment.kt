package com.atm1504.gosocio.ui.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.atm1504.gosocio.R
import com.atm1504.gosocio.utils.utils
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val PREFS_NAME = "atm"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dummy data storing. Would be actually present in logi page, after the api is ready
        val sharedPref: SharedPreferences =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("name", "Atreyee Mukherjee")
        editor.putString("email", "me@atm1504.in")
        editor.putString("phone", "8967570983")
        editor.putFloat("coins", 50.0F)
        editor.putInt("stick1", 2)
        editor.putInt("stick2", 1)
        editor.putInt("stick3", 0)
        editor.putInt("stick4", 7)
        editor.putInt("stick5", 9)
        editor.putBoolean("loggedIn", true)
        editor.commit()

        setupIntents()

    }

    fun setupIntents() {
        move_submit_report.setOnClickListener {
            utils.showToast(requireContext(),"Clciked1")
            findNavController().navigate(R.id.action_nav_home_to_nav_share, null)
        }

        move_to_profile.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_tools, null)
        }

        move_to_reports.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_reports_submitted, null)
        }

        move_to_mygov.setOnClickListener {
            //                https://play.google.com/store/apps/details?id=in.mygov.mobile&hl=en

            val appPackageName: String = "in.mygov.mobile&hl=en"


            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
    }
}