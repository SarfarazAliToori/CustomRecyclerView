package com.example.customrecyclerview

import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog_add_update.*
import kotlinx.android.synthetic.main.custom_dialog_add_update.view.*
import java.lang.reflect.Type
import javax.security.auth.login.LoginException

class MainActivity : AppCompatActivity() {

    var myAddapter : MyAddapter? = null

    var isTrue : Boolean = false

//    var myArrayList = arrayListOf(
//        MyDataClass(R.drawable.local_cafe, "This is Cup Title", "This is my Cup Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.bike, "This is Bike Title", "This is my Bike Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.ic_baseline_local_printshop_24, "This is Printer Title", "This is my Printer Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.laptop_mac, "This is Mac Title", "This is my Mac Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.local_bar, "This is Local Bar Title", "This is my Local Bar Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.baseball, "This is BaseBall Title", "This is my BaseBall Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.phone_android, "This is Android Phone Title", "This is my Android Phone Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.live_tv, "This is Screen Title", "This is my Screen Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.local_cafe, "This is Cafe Title", "This is my Cafe Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.bike, "This is Bike Title", "This is my Bike Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.ic_baseline_local_printshop_24, "This is Printer Title", "This is my Car Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.laptop_mac, "This is Mac Title", "This is my Mac Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.car, "This is Car Title", "This is my Car Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.camera, "This is Camera Title", "This is my Camera Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.local_bar, "This is Local Bar Title", "This is my Local Bar Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.baseball, "This is BaseBall Title", "This is my BaseBall Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//        MyDataClass(R.drawable.phone_android, "This is Android Phone Title", "This is my Android Phone Description Bro How are you. I am good And How are Your, Bro this is dummy text for product Description"),
//
//        // MyDataClass(R.drawable.bike, R.string.title.toString(), R.string.title2.toString())
//    )

    var myArrayList  = arrayListOf(MyDataClass(R.drawable.car, "Default", "Details of Car"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "All Products"

        myFilter()
        addProductData()

        val sharedPreferences1 = getSharedPreferences("MyFile", MODE_PRIVATE)
        if (!sharedPreferences1.getBoolean("FIRST_RUN", false)) {
            // Once run Code : from line no 63 to line no 81 Run Only Once when app is installed.
            myArrayList.addAll(listOf(MyDataClass(R.drawable.camera, "title", "Description")))
            recycler_view.layoutManager = LinearLayoutManager(this)
            myAddapter = MyAddapter(myArrayList, this)
            recycler_view.adapter = myAddapter

            myAddapter?.notifyItemInserted(myArrayList.size-1)
            recycler_view.scrollToPosition(myArrayList.size-1)

            //Save Data in SharePreferences first time
            val myEditor = sharedPreferences1.edit()
            val gson = Gson()
            val strGson : String = gson.toJson(myArrayList)
            myEditor.putString("list_key", strGson)
            myEditor.apply()
            myEditor.putBoolean("FIRST_RUN", true)
            myEditor.apply()
        }

        // Receive data from SharePreferences.
        if (sharedPreferences1.getBoolean("FIRST_RUN", true)) {
            val gson1 = Gson()
            val gsonStr = sharedPreferences1.getString("list_key", null)
            val type: Type = object : TypeToken<ArrayList<MyDataClass>>() {}.type
            myArrayList = gson1.fromJson(gsonStr, type)

            recycler_view.layoutManager = LinearLayoutManager(this)
            myAddapter = MyAddapter(myArrayList, this)
            recycler_view.adapter = myAddapter
        }
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

    fun addProductData() {
        btn_my_action.setOnClickListener {

            val myDialogLayout = View.inflate(this, R.layout.custom_dialog_add_update, null)
            val myAlertDialog = AlertDialog.Builder(this)
                .setView(myDialogLayout).show()
            myAlertDialog.btn_cus_dia_add_update.text = "Add"

            myDialogLayout.btn_cus_dia_add_update.setOnClickListener {


                val title = myDialogLayout.cus_edit_text_title.text.toString()
                val desc = myDialogLayout.cus_edit_text_description.text.toString()

                myArrayList.addAll(listOf(MyDataClass(R.drawable.camera, title, desc)))
                //myArrayList.add(MyDataClass(R.drawable.camera, title, desc))
               // myArrayList?.add(MyDataClass(R.drawable.car, "Default", "Default Detail"))
                myAddapter?.notifyItemInserted(myArrayList.size-1)
                recycler_view.scrollToPosition(myArrayList.size-1)

                isTrue = true

                //Save Data in SharePreferences
                val sharedPreferences = getSharedPreferences("MyFile", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val gson = Gson()
                val strGson : String = gson.toJson(myArrayList)
                editor.putString("list_key", strGson)
                editor.apply()
                Log.i("TAG", strGson)
                Log.i("TAG", "//////////////////////////////////////////////////////")

                val sharedPreferences1 = getSharedPreferences("MyFile", MODE_PRIVATE)
                val gson1 = Gson()
                val gsonStr = sharedPreferences1.getString("list_key", null)
                val type : Type = object : TypeToken<ArrayList<MyDataClass>>() {}.type
                myArrayList = gson1.fromJson(gsonStr, type)

                recycler_view.layoutManager = LinearLayoutManager(this)
                myAddapter = MyAddapter(myArrayList, this)
                recycler_view.adapter = myAddapter
                Log.i("TAG",  "last: ${gsonStr.toString()}")

                Log.i("TAG", "myArray: $myArrayList")

                Log.i("TAG", "==================================")

                var myAr  = arrayListOf(MyDataClass(R.drawable.car, "haahha", "hahahhaha of Car"))
                Log.i("TAG", "myAr : $myAr")


                myAlertDialog.dismiss()
            }

            myDialogLayout.btn_cus_cancel.setOnClickListener {
                myAlertDialog.dismiss()
            }

        }
    }

}