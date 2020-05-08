package com.atm1504.gosocio.ui.QRScanner

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.atm1504.gosocio.R
import com.atm1504.gosocio.ui.Prescription.UploadPrescriptionActivity
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.fragment_doctor_dashboard.*
import kotlinx.android.synthetic.main.fragment_qr_scanner.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.jar.Manifest

/**
 * A simple [Fragment] subclass.
 */
class qrScannerFragment : Fragment() {

    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource:CameraSource
    private var REQUEST_CAMERA_PERMISSION:Int = 201
    private var intentDta :String = ""




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_qr_scanner, container, false)
    return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    fun initialiseDetectorsAndSources(){
        Toast.makeText(context, "Barcode scanner started", Toast.LENGTH_SHORT).show()
        barcodeDetector = BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(context,barcodeDetector).setRequestedPreviewSize(1920,1080).setAutoFocusEnabled(true).build()

        this.surfaceView!!.holder!!.addCallback(object :SurfaceHolder.Callback{
            override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

            }

            override fun surfaceDestroyed(p0: SurfaceHolder?) {
               cameraSource.stop()
            }

            override fun surfaceCreated(p0: SurfaceHolder?) {
                openCamera()
            }

        }



        )
        this.barcodeDetector.setProcessor(object :Detector.Processor<Barcode>{
            override fun release() {
                Toast.makeText(context, "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            override fun receiveDetections(p0: Detector.Detections<Barcode>?) {
                val barcode : SparseArray<Barcode> = p0!!.detectedItems
                if (barcode.size()>0){
                    setBarCode(barcode)
                }

            }

        })
    }
    fun openCamera(){
        try {
            if (ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            cameraSource.start(surfaceView.holder)
            } else {
                ActivityCompat.requestPermissions(Activity(), arrayOf(android.Manifest.permission.CAMERA),REQUEST_CAMERA_PERMISSION)
            }
        } catch (e:IOException){
            e.printStackTrace()
        }

    }

    fun setBarCode(barcode:SparseArray<Barcode>){
        txtBarcodeValue?.post(object :Runnable {
            override fun run() {
                intentDta = barcode.valueAt(0).displayValue
                txtBarcodeValue.text =intentDta
                copyToClipBoard(intentDta)
                val intent = Intent(context, UploadPrescriptionActivity::class.java);
                startActivity(intent);
            }

        } )
    }

    override fun onPause() {
        super.onPause()
        cameraSource.release()
    }

    override fun onResume() {
        super.onResume()
        initialiseDetectorsAndSources()
    }

    fun copyToClipBoard(text: String){
        val clipboardManager:ClipboardManager
        clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData:ClipData = ClipData.newPlainText("QR code Scanner",text)
        clipboardManager.setPrimaryClip(clipData)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.size>0){
            if (grantResults[0] != PackageManager.PERMISSION_DENIED)


                 openCamera()
        }
    }
}
