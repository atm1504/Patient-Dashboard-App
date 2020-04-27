package com.atm1504.gosocio.ui.SubmittedReports

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.ReportedTasksResponse
import com.atm1504.gosocio.api.RetrofitApi
import com.atm1504.gosocio.api.SignupResponse
import kotlinx.android.synthetic.main.fragment_submitted_reports.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Response

class SubmittedReportsFragment : Fragment() {

    private lateinit var submittedReportsViewModel: SubmittedReportsViewModel
    private lateinit var submittedReportsAdapter: SubmittedReportsAdapter
    private lateinit var submittedReportsRecycler: RecyclerView
    private var progressDialog: ProgressDialog? = null
    private val PREFS_NAME = "atm"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        submittedReportsViewModel =
            ViewModelProviders.of(this).get(SubmittedReportsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_submitted_reports, container, false)
        submittedReportsRecycler = root.findViewById(R.id.submitted_reports_recycler_view)
        submittedReportsAdapter = SubmittedReportsAdapter()
        submittedReportsRecycler.layoutManager = LinearLayoutManager(context)
        submittedReportsRecycler.adapter = submittedReportsAdapter
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        message.visibility = View.GONE

        fetchReports()

    }

    fun fetchReports() {

        progressDialog = ProgressDialog(context)
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Getting list of all reports")
        progressDialog?.show()

        val retrofitApi = RetrofitApi.create()
        val sharedPref: SharedPreferences =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val email = RequestBody.create(
            MediaType.parse("text/plain"),
            sharedPref.getString("email", "me@atm1504.in ")
        )
        val access_token = RequestBody.create(
            MediaType.parse("text/plain"),
            sharedPref.getString("access_token", "djhwtuyeftef")
        )

        val call = retrofitApi.getReportedTasks(email, access_token)

        call.enqueue(object : retrofit2.Callback<ReportedTasksResponse> {
            override fun onFailure(call: retrofit2.Call<ReportedTasksResponse>, t: Throwable) {
                Log.d("KHANKI", "Failed")
                message.visibility = View.VISIBLE
                message.text = "SOmethign went wrong please try again"
                progressDialog?.dismiss()
            }

            override fun onResponse(
                call: retrofit2.Call<ReportedTasksResponse>,
                response: Response<ReportedTasksResponse>
            ) {
                progressDialog?.dismiss()
                Toast.makeText(context, "fetched", Toast.LENGTH_LONG).show()
                Log.d("KHANKI", response.body()?.toString())
                if (response.body()?.status == 200) {
                    val reports = response.body()?.reports
                    if (reports != null) {
                        message.visibility = View.GONE
                        submittedReportsAdapter.setSumittedReportsList(reports)
                    } else {
                        message.visibility = View.VISIBLE
                        message.text = "No reports to show"
                    }
                } else {
                    message.visibility = View.VISIBLE
                    message.text = "SOmethign went wrong please try again"
                }
            }

        })

    }
}