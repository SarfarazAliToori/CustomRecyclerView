package com.example.customrecyclerview

import android.widget.Toast

class MySingleton private constructor(){

    companion object {
        val mySingletonArryList = arrayListOf<MyDataClass>(
            MyDataClass(R.drawable.camera, "Camera", "Camera Detail"),
            MyDataClass(R.drawable.car, "Car", "Camera Detail"),
            MyDataClass(R.drawable.local_bar, "Bar", "Bar Detail"),
            MyDataClass(R.drawable.local_cafe, "Cafe", "Cafe Detail")
        )
        fun get() : MySingleton {
            val mySingleton = MySingleton()
            return mySingleton
        }
}
}


// another method of singleton
object MySingleT {
    //fun addition(a : Int,  b : Int) = a.plus(b)

    val mySingletonArryList = arrayListOf<MyDataClass>(
        MyDataClass(R.drawable.camera, "Camera", "Camera Detail"),
        MyDataClass(R.drawable.car, "Car", "Camera Detail"),
        MyDataClass(R.drawable.local_bar, "Bar", "Bar Detail"),
        MyDataClass(R.drawable.local_cafe, "Cafe", "Cafe Detail"))
}