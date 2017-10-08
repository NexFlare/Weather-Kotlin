package com.nexflare.weatherapp.Fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nexflare.weatherapp.API.WeatherAPI
import com.nexflare.weatherapp.Model.WeatherResponse
import com.nexflare.weatherapp.Adapter.PagerAdapter
import com.nexflare.weatherapp.R
import com.nexflare.weatherapp.Utils.PrefrenceManager
import com.nexflare.weatherapp.Utils.RetrofitSingelton
import kotlinx.android.synthetic.main.fragment_weather.*
import retrofit2.Call
import retrofit2.Response


/**
 * Created by nexflare on 04/10/17.
 */
class WeatherFragment : Fragment() {
    private var viewFragment: View? = null
    private lateinit var weatherApi: WeatherAPI
    private lateinit var mPref: PrefrenceManager
    private lateinit var dailyWeatherFragment: DailyWeatherFragment
    private lateinit var currentWeatherFragment: CurrentWeatherFragment

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewFragment = inflater?.inflate(R.layout.fragment_weather, container, false)
        weatherApi = RetrofitSingelton.getInstance().weatherAPI
        return viewFragment
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intializeComponents()
        mPref = PrefrenceManager(activity)

        val dailyWeatherF = childFragmentManager.findFragmentByTag("android:switcher:" + com.nexflare.weatherapp.R.id.weatherVP + ":" + weatherVP.currentItem)
        if (dailyWeatherF != null) {
            Log.d("TAG", "Found")
        } else {
            Log.d("TAG", "Not Found")
        }

        weatherVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        currentTv.setTextColor(Color.BLACK)
                        dailyTv.setTextColor(resources.getColor(R.color.unSelected))
                        weatherVP.currentItem = 0
                        val dailyWeatherF = childFragmentManager.findFragmentByTag("android:switcher:" + com.nexflare.weatherapp.R.id.weatherVP + ":0")
                        if (dailyWeatherF != null) {
                            Log.d("TAG", "0 Found")
                        } else {
                            Log.d("TAG", "0 Not Found")
                        }
                    }
                    1 -> {
                        currentTv.setTextColor(resources.getColor(R.color.unSelected))
                        dailyTv.setTextColor(Color.BLACK)
                        weatherVP.currentItem = 1
                        val dailyWeatherF = childFragmentManager.findFragmentByTag("android:switcher:" + com.nexflare.weatherapp.R.id.weatherVP + ":1")
                        if (dailyWeatherF != null) {
                            Log.d("TAG", " 1 Found")
                        } else {
                            Log.d("TAG", "1 Not Found")
                        }
                    }
                }
            }

        })
    }

    private fun intializeComponents() {
        val pageAdapter = PagerAdapter(childFragmentManager, null)
        weatherVP.adapter = pageAdapter
        weatherVP.currentItem = 0
        weatherVP.offscreenPageLimit = 2
        pageAdapter.notifyDataSetChanged()
    }

    fun callApi() {
        weatherApi.getWeatherResponse(mPref.getLatitude() + "," + mPref.getLongitude()).enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                Toast.makeText(activity, "some error occurred", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                Log.d("TAGGER", response?.body().toString())
                val currentWeather = Bundle()
                currentWeather.putSerializable("weather", response?.body())
                currentWeatherFragment.arguments = currentWeather
                currentWeatherFragment.printObject()
            }

        })
    }


    companion object {
        fun newInstance() = WeatherFragment()

    }

}
