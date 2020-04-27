package com.atm1504.gosocio.ui.submitReport

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.*
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.LoginResponse
import com.atm1504.gosocio.api.RetrofitApi
import com.atm1504.gosocio.api.RoadsResponse
import com.atm1504.gosocio.api.SubmitReportResponse
import com.atm1504.gosocio.utils.LocationHelper
import com.atm1504.gosocio.utils.utils
import kotlinx.android.synthetic.main.fragment_submit_report.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SubmitReportFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var submitReportViewModel: SubmitReportViewModel
    private var progressDialog: ProgressDialog? = null
    val PERMISSION_ID = 42
    lateinit var roads: List<String>
    var spinner: Spinner? = null
    var road_selected = ""
    var latitude: Double = 0.0
    var is_at_site = 1
    var longitude: Double = 0.0
    var complain = ""
    private lateinit var locationHelper: LocationHelper
    private val CAMERA = 2;
    private val GALLERY = 1;
    private val RECORD_REQUEST_CODE = 101
    private lateinit var bitmapImage: Bitmap
    private val PREFS_NAME = "atm"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        submitReportViewModel =
            ViewModelProviders.of(this).get(SubmitReportViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_submit_report, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = ProgressDialog(context)
        progressDialog?.setCancelable(false)
        locationHelper = LocationHelper(requireContext())
        submit_report.setBackgroundColor(
            ContextCompat.getColor(
                this.requireContext(),
                R.color.grey
            )
        )
        submit_report.isEnabled = false
        show_loaded_image.visibility=View.GONE

        // Taking permissions
        if (!checkPermissions()) {
            requestPermissions()
        }

        // Fetching roads data
        getRoads()

        getLocation()

        take_photo.setOnClickListener {
            reportReport()
        }

        submit_report.setOnClickListener {
            addReport()
        }

    }

    // Function to fetch the list of roads
    private fun getRoads() {

        progressDialog?.setMessage("Getting roads...")
        progressDialog?.show()

        val retofitApi = RetrofitApi.create()

        val call = retofitApi.getRoads()
        call.enqueue(object : Callback<RoadsResponse> {
            override fun onFailure(call: Call<RoadsResponse>, t: Throwable) {
                progressDialog?.dismiss()
            }

            override fun onResponse(call: Call<RoadsResponse>, response: Response<RoadsResponse>) {
                Log.d("KHANKI", "Fetched roads")
                progressDialog?.dismiss()
                roads = response.body()?.roads as List<String>
                loadSpinner()

            }
        })
    }

    // Load the spinner
    fun loadSpinner() {
        spinner = this.road_spinner
        spinner?.setOnItemSelectedListener(this)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, roads) }
        // Set layout to use when the list of choices appear
        aa?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner?.setAdapter(aa)
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        road_selected = roads[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_ID
            )
        }
    }

    fun reportReport() {
        var complain = write_complain.text
        if (complain.length < 1) {
            utils.showToast(context, "Please write the complain")
        } else {

            if (is_at_location.isChecked) {
                is_at_site = 1
            } else {
                is_at_site = 0
            }

            handleImage()

        }
    }

    fun handleImage() {
        captureImages()

    }

    private fun getLocation() {
        locationHelper.getLocation()
        if (locationHelper.canGetLocation()) {
            latitude = locationHelper.latitude
            longitude = locationHelper.longitude
        }
    }

    private fun captureImages() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle(getString(R.string.select_image))
        pictureDialog.setMessage(getString(R.string.photo_road))
        pictureDialog.setPositiveButton(
            getString(R.string.gallery),
            DialogInterface.OnClickListener { dialog, which -> choosePhotoFromGallery() }
        )

        pictureDialog.setNegativeButton(
            getString(R.string.camera),
            DialogInterface.OnClickListener { dialog, which ->
                // If user click no
                // then dialog box is canceled.
                getPhotoFromCamera()
            })

        val alertDialog = pictureDialog.create()
        alertDialog.show()
    }

    private fun getPhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    private fun choosePhotoFromGallery() {
        val galIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galIntent, GALLERY)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentUri = data.data
                val bitmap =
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentUri)
                bitmapImage = bitmap
                showImage()
            }
        } else if (requestCode == CAMERA) {
            val thumbnail = data?.extras?.get("data")
            bitmapImage = thumbnail as Bitmap
            showImage()
        }
    }

    fun showImage() {
        submit_report.setBackgroundColor(
            ContextCompat.getColor(
                this.requireContext(),
                R.color.orange
            )
        )
        submit_report.isEnabled = true
        show_loaded_image.visibility=View.VISIBLE

        show_loaded_image.setImageBitmap(bitmapImage)



    }

    fun addReport() {
        utils.showToast(context, "Image captured")
        val sharedPref: SharedPreferences =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(
            MediaType.parse("text/plain"),
            sharedPref.getString("email", "me@atm1504.in ")
        )
        val access_token = RequestBody.create(
            MediaType.parse("text/plain"),
            sharedPref.getString("access_token", "djhwtuyeftef")
        )
        val road_name = RequestBody.create(MediaType.parse("text/plain"), road_selected)
        val latitude = RequestBody.create(MediaType.parse("text/plain"), latitude.toString())
        val longitude = RequestBody.create(MediaType.parse("text/plain"), longitude.toString())
        val at_site = RequestBody.create(MediaType.parse("text/plain"), is_at_site.toString())
        val image = RequestBody.create(MediaType.parse("text/plain"), bitmapImage.toString())
        val complain = RequestBody.create(MediaType.parse("text/plain"), complain)

        val call = retofitApi.submitReport(
            email,
            access_token,
            complain,
            road_name,
            latitude,
            longitude,
            at_site,
            image
        )
        call.enqueue(object : Callback<SubmitReportResponse> {
            override fun onFailure(call: Call<SubmitReportResponse>, t: Throwable) {
                Log.d("KHANKI", "Failed to fetch data")
            }

            override fun onResponse(
                call: Call<SubmitReportResponse>,
                response: Response<SubmitReportResponse>
            ) {
                utils.showToast(context, "Submittted report")
            }


        })
    }

}