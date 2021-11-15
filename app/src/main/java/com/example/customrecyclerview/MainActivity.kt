package com.example.customrecyclerview

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
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog_add_update.*
import kotlinx.android.synthetic.main.custom_dialog_add_update.view.*

class MainActivity : AppCompatActivity() {

    var myAddapter : MyAddapter? = null

    val myArrayList = arrayListOf(
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

        // MyDataClass(R.drawable.bike, R.string.title.toString(), R.string.title2.toString())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "All Products"

        recycler_view.layoutManager = LinearLayoutManager(this)
        //recycler_view.adapter = MyAddapter(myArrayList, this)
        myAddapter = MyAddapter(myArrayList, this)
        recycler_view.adapter = myAddapter
        myFilter()
        addProductData()


        val sharedPreferences = getSharedPreferences("myFile", MODE_PRIVATE)
        val myGsonObject = sharedPreferences.getString("my_string", " ")
        val gson : Gson = Gson()
        val myGsonString : MyDataClass = gson.fromJson(myGsonObject, MyDataClass::class.java)

        myArrayList.addAll(listOf(myGsonString))

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

                val sharedPreferences = getSharedPreferences("myFile", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val gson : Gson = Gson()
                val mGson = gson.toJson(MyDataClass(R.drawable.car,title, desc))
                editor.putString("my_string", mGson)
                editor.apply()
                editor.commit()

                val myGsonObject = sharedPreferences.getString("my_string", null)
                val myGsonString : MyDataClass = gson.fromJson(myGsonObject, MyDataClass::class.java)

                myArrayList.addAll(listOf(myGsonString))

                Log.i("Hello",myArrayList.toString() )
                myAddapter?.notifyItemInserted(myArrayList.size-1)
                recycler_view.scrollToPosition(myArrayList.size-1)

                myAlertDialog.dismiss()
            }

            myDialogLayout.btn_cus_cancel.setOnClickListener {
                myAlertDialog.dismiss()
            }

        }
    }

}