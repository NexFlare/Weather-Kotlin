package com.nexflare.weatherapp.Activity

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.nexflare.weatherapp.R
import com.nexflare.weatherapp.Utils.LocationUtil
import com.nexflare.weatherapp.API.WeatherAPI
import com.nexflare.weatherapp.Interface.LocationChangedListener
import com.nexflare.weatherapp.Model.WeatherResponse
import com.nexflare.weatherapp.Adapter.PagerAdapter
import com.nexflare.weatherapp.Utils.PrefrenceManager
import com.nexflare.weatherapp.Utils.RetrofitSingelton
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val REQUEST_LOCATION: Int = 2309
    private var REQUEST_CHECK_SETTINGS = 0x1
    lateinit var locationUtil: LocationUtil
    lateinit var mPref: PrefrenceManager

    private lateinit var weatherApi: WeatherAPI
    private lateinit var pageAdapter: PagerAdapter
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.statusBarColor = Color.DKGRAY
        mPref = PrefrenceManager.newIntance(this)
        intializeComponents()
        checkForPermission()

       swipeLayout.setOnRefreshListener {
           Toast.makeText(this@MainActivity, "Refreshing Layout", Toast.LENGTH_SHORT).show()
           Log.d("TAGGER ", "Something is missing")
           swipeLayout.isRefreshing = false
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

                    }
                    1 -> {
                        currentTv.setTextColor(resources.getColor(R.color.unSelected))
                        dailyTv.setTextColor(Color.BLACK)
                        weatherVP.currentItem = 1

                    }
                }
            }

        })
    }

    private fun intializeComponents() {
        pageAdapter = PagerAdapter(supportFragmentManager, null)
        weatherVP.adapter = pageAdapter
        weatherVP.currentItem = 0
        weatherVP.offscreenPageLimit = 2
    }

    /*private fun intializeComponents(weatherResponse: WeatherResponse?) {
        pageAdapter= PagerAdapter(supportFragmentManager,weatherResponse)
        weatherVP.adapter=pageAdapter
        weatherVP.currentItem = 0
        weatherVP.offscreenPageLimit=2
    }*/


    fun checkForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
            } else
                getLocation()
        }
    }

    fun getLocation() {
        locationUtil = LocationUtil(this@MainActivity, object : LocationChangedListener {
            override fun onLocationChanged(latitude: Double, longitude: Double) {
                Toast.makeText(this@MainActivity, "" + latitude + " " + longitude, Toast.LENGTH_SHORT).show()
                locationUtil.googleApiClient.disconnect()
                PrefrenceManager.newIntance(this@MainActivity).setLatitude(latitude.toString())
                PrefrenceManager.newIntance(this@MainActivity).setLongitude(longitude.toString())
                requestWeather()
            }
        }

        )

    }

    private fun requestWeather() {
        val progessDialog = ProgressDialog(this@MainActivity, ProgressDialog.STYLE_SPINNER)
        progessDialog.setMessage("Please wait ...")
        progessDialog.setCancelable(false)
        progessDialog.show()
        weatherApi = RetrofitSingelton.getInstance().weatherAPI
        weatherApi.getWeatherResponse(mPref.getLatitude() + "," + mPref.getLongitude()).enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                progessDialog.dismiss()
                //intializeComponents(null)
                Toast.makeText(this@MainActivity, "some error occurred", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                Log.d("TAGGER", response?.body().toString())
                //intializeComponents(response?.body())
                pageAdapter.weatherResponse = response?.body()
                pageAdapter.currentWeatherFragmant.currentWeather = response?.body()?.currently
                pageAdapter.currentWeatherFragmant.timeZone=response?.body()?.timezone
                pageAdapter.currentWeatherFragmant.updateUI()
                pageAdapter.dailyWeatherFragment.dailyWeatherList = response?.body()?.daily
                pageAdapter.dailyWeatherFragment.timeZone=response?.body()?.timezone
                pageAdapter.dailyWeatherFragment.updateUI()
                progessDialog.dismiss()
            }

        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                when (resultCode) {
                    Activity.RESULT_CANCELED -> finish()

                    else -> if (locationUtil.googleApiClient.isConnected) {
                        locationUtil.getLocation()
                    }
                }
            }
        }
    }
}
