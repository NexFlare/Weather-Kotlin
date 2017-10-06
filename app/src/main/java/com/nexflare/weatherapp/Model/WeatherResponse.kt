package com.nexflare.weatherapp.Model

import java.io.Serializable

/**
 * Created by nexflare on 01/10/17.
 */
data class WeatherResponse(val currently: CurrentWeather, val daily: DailyWeatherList,val timezone:String):Serializable

data class CurrentWeather(val time:Long, val temperature:Double,
                          var icon:String, val humidity:Double,
                          val pressure:Double, val summary:String,
                          val windSpeed:Double,val precipProbability:Double,
                          val precipType:String)

data class DailyWeatherList(val summary:String,val icon:String,val data:ArrayList<DailyWeather>)

data class DailyWeather(val time:Long,val summary: String,val icon: String)