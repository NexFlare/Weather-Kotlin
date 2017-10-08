package com.nexflare.weatherapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexflare.weatherapp.Adapter.DailyWeatherAdapter
import com.nexflare.weatherapp.Model.DailyWeather
import com.nexflare.weatherapp.Model.DailyWeatherList
import com.nexflare.weatherapp.R
import kotlinx.android.synthetic.main.daily_weather_fragment.*


/**
 * Created by nexflare on 05/10/17.
 */

class DailyWeatherFragment:Fragment(){
    var dailyWeatherList:DailyWeatherList?=null
    lateinit var adapter:DailyWeatherAdapter
    var timeZone:String?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.daily_weather_fragment,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= DailyWeatherAdapter(activity,null,null)
        dailyRv.layoutManager = LinearLayoutManager(activity)
        dailyRv.adapter=adapter
    }

    fun updateUI(){
        adapter.updateList(dailyWeatherList?.data,timeZone)
    }

    companion object {
        fun newInstance(dailyWeatherList: DailyWeatherList?,timeZone:String?):DailyWeatherFragment{
            val dailyFragmentWeather=DailyWeatherFragment()
            dailyFragmentWeather.dailyWeatherList=dailyWeatherList
            dailyFragmentWeather.timeZone=timeZone
            return dailyFragmentWeather
        }
    }
}