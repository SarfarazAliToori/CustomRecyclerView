package com.example.customrecyclerview

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val myArrayList = listOf<MyDataClass>(
        MyDataClass(R.drawable.local_cafe, "This is Cup Title", "This is my Cup Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.bike, "This is Bike Title", "This is my Bike Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.ic_baseline_local_printshop_24, "This is Printer Title", "This is my Printer Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.laptop_mac, "This is Mac Title", "This is my Mac Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.local_bar, "This is Local Bar Title", "This is my Local Bar Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.baseball, "This is BaseBall Title", "This is my BaseBall Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.phone_android, "This is Android Phone Title", "This is my Android Phone Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.live_tv, "This is Screen Title", "This is my Screen Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.local_cafe, "This is Cafe Title", "This is my Cafe Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.bike, "This is Bike Title", "This is my Bike Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.ic_baseline_local_printshop_24, "This is Printer Title", "This is my Car Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.laptop_mac, "This is Mac Title", "This is my Mac Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.car, "This is Car Title", "This is my Car Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.camera, "This is Camera Title", "This is my Camera Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.local_bar, "This is Local Bar Title", "This is my Local Bar Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.baseball, "This is BaseBall Title", "This is my BaseBall Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.phone_android, "This is Android Phone Title", "This is my Android Phone Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
        MyDataClass(R.drawable.live_tv, "This is Screen Title", "This is my Screen Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),

        // MyDataClass(R.drawable.bike, R.string.title.toString(), R.string.title2.toString())
    )

    var myAddapter : MyAddapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "All Products"

        recycler_view.layoutManager = LinearLayoutManager(this)
        //recycler_view.adapter = MyAddapter(myArrayList, this)
        myAddapter = MyAddapter(myArrayList, this)
        recycler_view.adapter = myAddapter
        myFilter()

    }


    fun  myFilter() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
//                myAddapter?.filter?.filter(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                myAddapter?.filter?.filter(p0)
                return true
            }

        })
    }

}