package com.example.customrecyclerview

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_items_detail_screen.*
import java.util.jar.Manifest

class ItemsDetailScreen : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_detail_screen)
        title = "Product Detail"

        receiveDataFromMainAct()
        accessPermition()
    }

    fun receiveDataFromMainAct() {
        img_view1.setImageResource(intent.getIntExtra("imageKey",R.drawable.car))
        tv_title1.setText(intent.getStringExtra("titleKey"))
        tv_detail.setText(intent.getStringExtra("descriptionKey"))
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_start_record -> startRecording()
            R.id.btn_stop_record -> stopRecording()
            R.id.btn_Play_record -> playRecording()
        }
    }

    fun accessPermition() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 110)
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode==110 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//    }

    fun startRecording() {

    }

    fun stopRecording() {

    }

    fun playRecording() {

    }


}