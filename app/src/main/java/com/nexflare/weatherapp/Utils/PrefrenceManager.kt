package com.nexflare.weatherapp.Utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by nexflare on 05/10/17.
 */
class PrefrenceManager(val context:Context){
    private val sharedPref:SharedPreferences
    private val editor:SharedPreferences.Editor
    init {
        sharedPref=context.getSharedPreferences("WEATHERPREF",Context.MODE_PRIVATE)
        editor=sharedPref.edit()
    }

    fun setLatitude(latitude:String)= editor.putString("latitude",latitude).commit()


    fun getLatitude() = sharedPref.getString("latitude","")

    fun setLongitude(longitude:String)=editor.putString("longitude",longitude).commit()

    fun getLongitude()=sharedPref.getString("longitude","")

    companion object {
        private var instance:PrefrenceManager?=null
        fun newIntance(context: Context):PrefrenceManager{
            if (instance==null) instance= PrefrenceManager(context)
            return instance as PrefrenceManager
        }
    }
}