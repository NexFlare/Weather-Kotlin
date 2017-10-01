package com.nexflare.weatherapp

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by nexflare on 01/10/17.
 */
interface WeatherAPI {
    @GET("{location}")
    fun getWeatherResponse(location:String): Call<WeatherResponse>
}