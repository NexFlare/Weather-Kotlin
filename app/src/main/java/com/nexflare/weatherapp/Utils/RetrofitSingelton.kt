package com.nexflare.weatherapp.Utils

import com.nexflare.weatherapp.API.WeatherAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by nexflare on 01/10/17.
 */

class RetrofitSingelton private constructor() {

    lateinit var weatherAPI: WeatherAPI

    private val BASE_URL = "https://api.darksky.net/forecast/7c1c8dd09cd903add4de5cc32a8fbed0/"

    init {

        buildApi()
    }

    private fun buildApi() {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        this.weatherAPI = retrofit.create(WeatherAPI::class.java)
    }
// static variables in kotlin
companion object {
    private var inst: RetrofitSingelton? = null
    fun getInstance(): RetrofitSingelton {
        if (inst == null)
            inst = RetrofitSingelton()
        return inst as RetrofitSingelton
    }
}



}
