package com.nexflare.weatherapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by nexflare on 04/10/17.
 */
class WeatherFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View?= inflater?.inflate(R.layout.fragment_weather,container)
        return view
    }



    companion object {
        fun getInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }
}