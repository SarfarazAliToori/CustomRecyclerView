package com.example.customrecyclerview

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_items_detail_screen.*
import java.io.ByteArrayOutputStream
import java.io.IOException


private var path: String? = null
private var mRecorder: MediaRecorder? = null
private var state: Boolean = false
private const val REQUEST_CODE = 42

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
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_start_record -> startRecording()
            R.id.btn_stop_record -> stopRecording()
            R.id.btn_Play_record -> playRecording()
            R.id.btn_camera -> openCamera()
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
        val picIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (picIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(picIntent, REQUEST_CODE)
        } else {
            Toast.makeText(applicationContext, "Unable to open Camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val pic = data?.extras?.get("data") as Bitmap

            val bstream = ByteArrayOutputStream()

            // pic.compress(Bitmap.CompressFormat.JPEG,50,bstream)
            // val imgg = bstream.toByteArray()
            //val strimgImg = Base64.getEncoder(imgg,Base64.DEAFULT)

            img_view1.setImageBitmap(pic)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}


/////////////////////////////////////////