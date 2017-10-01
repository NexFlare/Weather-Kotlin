package com.nexflare.weatherapp

/**
 * Created by nexflare on 01/10/17.
 */
data class WeatherResponse(val currently:CurrentWeather,val daily:DailyWeatherList)

data class CurrentWeather(val time:Long, val temperature:Double,
                          val icon:String, val humidity:Double,
                          val pressure:Double, val summary:String)

data class DailyWeatherList(val summary:String,val icon:String,val data:ArrayList<DailyWeather>)

data class DailyWeather(val time:Long,val summary: String,val icon: String)