package com.atm1504.gosocio.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.atm1504.gosocio.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private val PREFS_NAME = "atm"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
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
        show_coins.text=sharedPref.getFloat("coins",0.0F).toString()
        stick1.text=sharedPref.getInt("stick1",0).toString()
        stick2.text=sharedPref.getInt("stick2",0).toString()
        stick3.text=sharedPref.getInt("stick3",0).toString()
        stick4.text=sharedPref.getInt("stick4",0).toString()
        stick5.text=sharedPref.getInt("stick5",0).toString()

    }
}