package com.nexflare.weatherapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexflare.weatherapp.Model.CurrentWeather
import com.nexflare.weatherapp.Model.WeatherResponse
import kotlinx.android.synthetic.main.fragment_current_weather.*
import com.nexflare.weatherapp.R

/**
 * Created by nexflare on 05/10/17.
 */

class CurrentWeatherFragment:Fragment(){
    var currentWeather:CurrentWeather?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TAGGER","Current Weather CALLED")
        return layoutInflater.inflate(R.layout.fragment_current_weather,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAGGER","Current Weather "+currentWeather)
        updateUI()
    }

    private fun updateUI() {
        when(currentWeather?.icon){
            "clear-day"->{

            }
        }
    }

    fun printObject(){
        val weatherResponse:WeatherResponse = arguments.getSerializable("weather") as WeatherResponse
        Log.d("TAGGER123",weatherResponse.currently.icon)
    }

    companion object {
       fun newInstance(currentWeather: CurrentWeather?):CurrentWeatherFragment{
           val currentWeatherFragment=CurrentWeatherFragment()
           currentWeatherFragment.currentWeather=currentWeather
           return currentWeatherFragment
       }
    }
}