package com.example.customrecyclerview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_items_detail_screen.*
import java.io.ByteArrayOutputStream
import java.io.IOException


private var path: String? = null
private var mRecorder: MediaRecorder? = null
private var state: Boolean = false
private const val REQUEST_CODE = 110

class ItemsDetailScreen : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_detail_screen)
        title = "Product Detail"
        val path = Environment.getExternalStorageDirectory().toString() + "/myRedoding.3gp"
        receiveDataFromMainAct()
        myInit()
        //accessPermition()
    }

    fun receiveDataFromMainAct() {
        img_view1.setImageResource(intent.getIntExtra("imageKey",R.drawable.car))
        tv_title1.setText(intent.getStringExtra("titleKey"))
        tv_detail.setText(intent.getStringExtra("descriptionKey"))
    }

    fun myInit() {
        btn_start_record.setOnClickListener(this)
        btn_stop_record.setOnClickListener(this)
        btn_Play_record.setOnClickListener(this)
        btn_camera.setOnClickListener(this)
        btn_image_Encode_decode.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_start_record -> startRecording()
            R.id.btn_stop_record -> stopRecording()
            R.id.btn_Play_record -> playRecording()
            R.id.btn_camera -> openCamera()
            R.id.btn_image_Encode_decode -> imageEncodeDecode()
        }
    }


    fun startRecording() {

        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val myPermission = arrayOf(
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, myPermission, 0)
        } else {

            try {

                path = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"
                Log.i("TAG", path.toString())
                mRecorder = MediaRecorder()

                mRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
                mRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                mRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                mRecorder?.setOutputFile(path)
                mRecorder?.prepare()
                mRecorder?.start()
                state = true
                Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    fun stopRecording() {

        try {
            if (state) {
                mRecorder?.stop()
                mRecorder?.release()
                state = false
            } else {
                Toast.makeText(this, "You are not recording right now!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playRecording() {

        try {
            var mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (cameraIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(cameraIntent, REQUEST_CODE)
        } else {
            Toast.makeText(applicationContext, "Unable to open Camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // receive pic from Intent.
            val pic = data?.extras?.get("data") as Bitmap

            val byteArrayOutputStream = ByteArrayOutputStream()
           // val bitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pf_image)
            pic.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            var imageByte : ByteArray = byteArrayOutputStream.toByteArray()
            var myImageStrig : String = Base64.encodeToString(imageByte, Base64.DEFAULT)
            tv_detail.text = myImageStrig

            imageByte = Base64.decode(myImageStrig, Base64.DEFAULT)
            val decodeImage : Bitmap = BitmapFactory.decodeByteArray(imageByte,0, imageByte.size)

            img_view1.setImageBitmap(decodeImage)


        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    fun imageEncodeDecode() {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val bitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pf_image)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        var imageByte : ByteArray = byteArrayOutputStream.toByteArray()
        var myImageStrig : String = Base64.encodeToString(imageByte, Base64.DEFAULT)
        tv_detail.text = myImageStrig

        imageByte = Base64.decode(myImageStrig, Base64.DEFAULT)
        val decodeImage : Bitmap = BitmapFactory.decodeByteArray(imageByte,0, imageByte.size)
        img_view1.setImageBitmap(decodeImage)

        val mySingleton = MySingleton.get()
        println("My singleton : $mySingleton")
        Log.i("TAG", mySingleton.toString())
    }

    // wifi status
    private val wifiStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN)) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    Toast.makeText(this@ItemsDetailScreen, "Wifi is On", Toast.LENGTH_LONG).show()
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    Toast.makeText(this@ItemsDetailScreen, "Wifi is Off", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)
    }

}


/////////////////////////////////////////