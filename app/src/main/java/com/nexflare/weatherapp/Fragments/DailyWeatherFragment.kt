package com.nexflare.weatherapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexflare.weatherapp.Model.DailyWeather
import com.nexflare.weatherapp.Model.DailyWeatherList
import com.nexflare.weatherapp.R


/**
 * Created by nexflare on 05/10/17.
 */

class DailyWeatherFragment:Fragment(){
    var dailyWeatherList:DailyWeatherList?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.daily_weather_fragment,container,false)
    }
    companion object {
        fun newInstance(dailyWeatherList: DailyWeatherList?):DailyWeatherFragment{
            val dailyFragmentWeather=DailyWeatherFragment()
            dailyFragmentWeather.dailyWeatherList=dailyWeatherList
            return dailyFragmentWeather
        }
    }
}