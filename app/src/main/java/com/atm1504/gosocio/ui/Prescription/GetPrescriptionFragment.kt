package com.atm1504.gosocio.ui.Prescription

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.PrescriptionResponse
import com.atm1504.gosocio.api.RetrofitApi
import kotlinx.android.synthetic.main.fragment_submitted_reports.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class GetPrescriptionFragment : Fragment() {

    private lateinit var prescriptionViewModel: PrescriptionViewModel
    private lateinit var prescriptionAdapter: PrescriptionAdapter
    private lateinit var prescriptionRecyclerView: RecyclerView
    private var progressDialog: ProgressDialog? = null
    private var PREFS_NAME = "neeraj"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prescriptionViewModel =
            ViewModelProviders.of(this).get(PrescriptionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_get_prescription, container, false)
        prescriptionRecyclerView = root.findViewById(R.id.prescription_recycler)
        prescriptionAdapter = PrescriptionAdapter()
        prescriptionRecyclerView.layoutManager = LinearLayoutManager(context)
        prescriptionRecyclerView.adapter = prescriptionAdapter
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        fetchPrescriptions()

    }

    fun fetchPrescriptions() {

        progressDialog = ProgressDialog(context)
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Getting list of all reports")
        progressDialog?.show()

        val retrofitApi = RetrofitApi.create()
        val sharedPref: SharedPreferences =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        /*val userID = RequestBody.create(
            MediaType.parse("text/plain"),
            sharedPref.getString("userid", "fjfkls")
        )*/
        val userID:String = "5eb6810674bdfb0017ed92dd"


        val call = retrofitApi.getPrescList(userID)

        call.enqueue(object : retrofit2.Callback<PrescriptionResponse> {
            override fun onFailure(call: Call<PrescriptionResponse>, t: Throwable) {
                progressDialog?.dismiss()
            }

            override fun onResponse(
                call: Call<PrescriptionResponse>,
                response: Response<PrescriptionResponse>
            ) {
                progressDialog?.dismiss()
                Toast.makeText(context, "fetched", Toast.LENGTH_LONG).show()
                Log.d("KHANKI", response.body().toString())

                    val reports = response.body()?.reports
                    if (reports != null) {
                        prescriptionAdapter.setPrescriptionList(reports)
                    }

            }


        })

    }
}