package com.nexflare.weatherapp.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.pwittchen.weathericonview.WeatherIconView
import com.nexflare.weatherapp.Model.DailyWeather
import com.nexflare.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by nexflare on 08/10/17.
 */
class DailyWeatherAdapter(var context: Context, var dailyWeatherList: ArrayList<DailyWeather>?, var timeZone: String?) : RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_daily, parent, false)
        return ViewHolder(view)
    }

    fun updateList(dailyWeatherList: ArrayList<DailyWeather>?, timeZone: String?) {
        this.dailyWeatherList = dailyWeatherList
        this.dailyWeatherList?.removeAt(0)
        this.timeZone = timeZone
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val dailyWeather = dailyWeatherList?.get(position)
        holder?.bindData(dailyWeather)
    }

    override fun getItemCount(): Int {
        return dailyWeatherList?.size ?: 0
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


        var temp = itemView?.findViewById<TextView>(R.id.tempLabel)
        var day = itemView?.findViewById<TextView>(R.id.dailyDayTv)
        var iconIv = itemView?.findViewById<WeatherIconView>(R.id.dailyIconIv)

        fun bindData(dailyWeather: DailyWeather?) {
            temp?.text = (dailyWeather?.temperatureMax?.minus(32))?.times(.5555)?.toInt().toString()
            when (getDay(dailyWeather?.time)) {
                "Mon" -> day?.text = "MONDAY"
                "Tue" -> day?.text = "TUESDAY"
                "Wed" -> day?.text = "WEDNESDAY"
                "Thu" -> day?.text = "THURSDAY"
                "Fri" -> day?.text = "FRIDAY"
                "Sat" -> day?.text = "SATURDAY"
                "Sun" -> day?.text = "SUNDAY"
                else -> getDay(dailyWeather?.time)
            }
            when (dailyWeather?.icon) {
                "clear-day" -> {
                    iconIv?.setIconResource(context.getString(R.string.wi_day_sunny))
                }
                "clear-night" -> {
                    iconIv?.setIconResource(context.getString(R.string.wi_forecast_io_clear_night))
                }
                "rain" -> {
                    iconIv?.setIconResource(context.getString(R.string.wi_rain))
                }
                "snow" -> {
                    iconIv?.setIconResource(context.getString(R.string.wi_snow))
                }
                "sleet" -> {
                    iconIv?.setIconResource(context.getString(R.string.wi_sleet))
                }
                "wind" -> {
                    iconIv?.setIconResource(context.getString(R.string.wi_strong_wind))
                }
                "cloudy" -> {
                    iconIv?.setIconResource(context.getString(R.string.wi_cloudy))
                }
                "partly-cloudy-day" -> {
                    iconIv?.setIconResource(context.getString(R.string.wi_day_cloudy))
                }
                "fog" -> {
                    iconIv?.setIconResource(context.getString(R.string.wi_forecast_io_fog))
                }
                "partly-cloudy-night" -> iconIv?.setIconResource(context.getString(R.string.wi_night_cloudy))
                "thunderstorm" -> iconIv?.setIconResource(context.getString(R.string.wi_thunderstorm))
            }
        }

    }

    private fun getDay(time: Long?): String {
        val timeNull = time ?: 0
        val date = Date(timeNull.times(1000))
        val simpleDateFormat = SimpleDateFormat("EEE")
        simpleDateFormat.timeZone = TimeZone.getTimeZone(timeZone ?: "America/Los_Angeles")
        return simpleDateFormat.format(date)
    }
}


