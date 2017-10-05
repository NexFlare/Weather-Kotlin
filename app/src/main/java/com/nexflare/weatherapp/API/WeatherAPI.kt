package com.nexflare.weatherapp.API

import com.nexflare.weatherapp.Model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by nexflare on 01/10/17.
 */
interface WeatherAPI {
    @GET("{location}")
    fun getWeatherResponse(@Path("location") location:String): Call<WeatherResponse>
}