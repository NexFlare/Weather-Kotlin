package com.nexflare.weatherapp.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import com.nexflare.weatherapp.Fragments.CurrentWeatherFragment
import com.nexflare.weatherapp.Fragments.DailyWeatherFragment
import com.nexflare.weatherapp.Model.WeatherResponse

/**
 * Created by nexflare on 06/10/17.
 */
class PagerAdapter(fm:FragmentManager, weatherResponse: WeatherResponse?):FragmentPagerAdapter(fm){


    lateinit var currentWeatherFragmant:CurrentWeatherFragment
    var weatherResponse=weatherResponse
    lateinit var dailyWeatherFragment:DailyWeatherFragment
    override fun getItem(position: Int): Fragment {
        when(position){
            0->{
                currentWeatherFragmant= CurrentWeatherFragment.newInstance(weatherResponse?.currently,weatherResponse?.timezone)
                return currentWeatherFragmant
            }
            1->{
                dailyWeatherFragment= DailyWeatherFragment.newInstance(weatherResponse?.daily,weatherResponse?.timezone)
                return dailyWeatherFragment
            }
            else->{
                currentWeatherFragmant= CurrentWeatherFragment.newInstance(weatherResponse?.currently,weatherResponse?.timezone)
                return currentWeatherFragmant
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

}