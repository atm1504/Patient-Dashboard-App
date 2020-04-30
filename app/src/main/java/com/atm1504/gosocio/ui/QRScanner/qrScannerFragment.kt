package com.atm1504.gosocio.ui.QRScanner

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.atm1504.gosocio.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.fragment_doctor_dashboard.*
import kotlinx.android.synthetic.main.fragment_qr_scanner.*
import org.json.JSONException
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class qrScannerFragment : Fragment() {

    private lateinit var qrscan:IntentIntegrator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_qr_scanner, container, false)
    return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        qrscan = IntentIntegrator(activity)
        scan_qr.setOnClickListener{
            qrscan.initiateScan()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null){
            if (result.contents ==null){
                Toast.makeText(context,"Result not Found",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context,"Result  Found",Toast.LENGTH_LONG).show()
            }

        }

    }


}
