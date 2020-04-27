package com.atm1504.gosocio.ui.reportDetails

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.ReportResponse
import com.atm1504.gosocio.api.RetrofitApi
import com.atm1504.gosocio.utils.utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_report_details.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReportDetailsFragment : Fragment() {
    private var progressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchComplain()

    }

    fun fetchComplain() {
        progressDialog = ProgressDialog(context)
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Getting Information...")
        progressDialog?.show()

        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(MediaType.parse("text/plain"), "gdger@eer")
        val access_token = RequestBody.create(MediaType.parse("text/plain"), "ewutuwe53b2s6")
        val id = RequestBody.create(MediaType.parse("text/plain"), arguments?.getString("id"))

        val call = retofitApi.getReport(email, access_token, id)
        call.enqueue(object : Callback<ReportResponse> {
            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                Log.d("KHANKI", "failed")
                utils.showToast(context, "Failed to fetch data. Please try again")
                progressDialog?.dismiss()
            }

            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {
                progressDialog?.dismiss()
                val body = response.body()
                if (body?.status == 200) {
                    Glide.with(requireContext())
                        .load(body.image)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .into(show_image)

                    show_road_name.text = "Road:" + body.road_name
                    show_coins.text = body.coins.toString() + " Coins!!"
                    show_status.text = "Status: " + body.status
                    show_complain.text = body.complain
                    show_location.text = body.latitude + " " + body.longitude
                } else {
                    utils.showToast(context, "Something went wrong, please try again")
                }
            }

        })
    }
}
