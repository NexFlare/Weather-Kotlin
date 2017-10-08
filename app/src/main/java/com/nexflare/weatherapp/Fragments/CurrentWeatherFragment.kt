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
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by nexflare on 05/10/17.
 */

class CurrentWeatherFragment:Fragment(){
    var currentWeather:CurrentWeather?=null
    var timeZone:String?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TAGGER","Current Weather CALLED")
        return layoutInflater.inflate(R.layout.fragment_current_weather,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAGGER","Current Weather "+currentWeather)
        updateUI()
    }

    fun updateUI() {
        Log.d("TAGGER TIME",timeZone?:"NULL")
        when(currentWeather?.icon){
            "clear-day"->{
                iconIv.setIconResource(getString(R.string.wi_day_sunny))

            }
            "clear-night"->{
                iconIv.setIconResource(getString(R.string.wi_forecast_io_clear_night))
            }
            "rain"->{
                iconIv.setIconResource(getString(R.string.wi_rain))
            }
            "snow"->{
                iconIv.setIconResource(getString(R.string.wi_snow))
            }
            "sleet"->{
                iconIv.setIconResource(getString(R.string.wi_sleet))
            }
            "wind"->{
                iconIv.setIconResource(getString(R.string.wi_strong_wind))
            }
            "cloudy"->{
                iconIv.setIconResource(getString(R.string.wi_cloudy))
            }
            "partly-cloudy-day"->{
                iconIv.setIconResource(getString(R.string.wi_day_cloudy))
            }
            "fog"->{
                iconIv.setIconResource(getString(R.string.wi_forecast_io_fog))
            }
            "partly-cloudy-night"-> iconIv.setIconResource(getString(R.string.wi_night_cloudy))
            "thunderstorm"->iconIv.setIconResource(getString(R.string.wi_thunderstorm))


        }
        humdityTv.text=currentWeather?.humidity?.times(100).toString()+" %"
        when(getDay(currentWeather?.time)){
            "Mon"-> dayTv.text="MONDAY"
            "Tue"->dayTv.text="TUESDAY"
            "Wed"->dayTv.text="WEDNESDAY"
            "Thu"->dayTv.text="THURSDAY"
            "Fri"->dayTv.text="FRIDAY"
            "Sat"->dayTv.text="SATURDAY"
            "Sun"->dayTv.text="SUNDAY"
            else->getDay(currentWeather?.time)
        }
        val tempCelius=(currentWeather?.temperature?.minus(32))?.times(.5555)?.toInt()
        tempLabel.text=tempCelius.toString()
        summaryTv.text=currentWeather?.summary?.toUpperCase()
        windSpeedTv.text = currentWeather?.windSpeed.toString()+" km/h"
        precipTv.text=(currentWeather?.precipProbability?.times(100)).toString()+" %"
        precipTypeTv.text=currentWeather?.precipType?.toUpperCase()?:"RAIN"
    }

    private fun  getDay(time: Long?): String {
        val timeNull=time?:0
        val date= Date(timeNull.times(1000))
        val simpleDateFormat=SimpleDateFormat("EEE")
        simpleDateFormat.timeZone= TimeZone.getTimeZone(timeZone?:"America/Los_Angeles")
        return simpleDateFormat.format(date)
    }

    fun printObject(){
        val weatherResponse:WeatherResponse = arguments.getSerializable("weather") as WeatherResponse
        Log.d("TAGGER123",weatherResponse.currently.icon)
    }

    companion object {
       fun newInstance(currentWeather: CurrentWeather?,timeZone:String?):CurrentWeatherFragment{
           val currentWeatherFragment=CurrentWeatherFragment()
           currentWeatherFragment.currentWeather=currentWeather
           currentWeatherFragment.timeZone=timeZone
           return currentWeatherFragment
       }
    }
}