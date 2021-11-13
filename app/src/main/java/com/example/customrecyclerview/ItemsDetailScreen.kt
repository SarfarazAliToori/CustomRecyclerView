package com.example.customrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_items_detail_screen.*

class ItemsDetailScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_detail_screen)
        title = "Product Detail"

        img_view1.setImageResource(intent.getIntExtra("imageKey",R.drawable.car))
        tv_title1.setText(intent.getStringExtra("titleKey"))
        tv_detail.setText(intent.getStringExtra("descriptionKey"))
    }
}