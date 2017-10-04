package com.nexflare.weatherapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by nexflare on 05/10/17.
 */

class CurrentWeatherFragment:Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_current_weather,container,false)
    }
    companion object {
        val instance:CurrentWeatherFragment
            get() = CurrentWeatherFragment()
    }
}