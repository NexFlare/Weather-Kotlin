package com.nexflare.weatherapp.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.nexflare.weatherapp.Fragments.WeatherFragment
import com.nexflare.weatherapp.R
import com.nexflare.weatherapp.Utils.LocationUtil
import com.nexflare.weatherapp.API.WeatherAPI

class MainActivity : AppCompatActivity() {
    private val REQUEST_LOCATION: Int = 2309
    private var REQUEST_CHECK_SETTINGS=0x1
    lateinit var locationUtil: LocationUtil
    private lateinit var weatherApi: WeatherAPI
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val window=window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.statusBarColor = Color.DKGRAY
        checkForPermission()
        supportFragmentManager.beginTransaction().add(R.id.containerFL, WeatherFragment.instance).commit()

    }


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
        /*locationUtil = LocationUtil(this@MainActivity, object : LocationChangedListener {
            override fun onLocationChanged(latitute: Double?, longitude: Double?) {
                Toast.makeText(this@MainActivity,""+latitute+" "+longitude,Toast.LENGTH_SHORT).show()
                locationUtil.googleApiClient.disconnect()
                weatherApi=RetrofitSingelton.getInstance().weatherAPI
                weatherApi.getWeatherResponse(latitute.toString()+","+longitude.toString()).enqueue(object :retrofit2.Callback<WeatherResponse> {
                    override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                        Toast.makeText(this@MainActivity,"some error occurred",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                        Log.d("TAGGER",response?.body().toString())
                    }

                })
            }
        }

        )*/

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
        when(requestCode){
            REQUEST_CHECK_SETTINGS->{
                when(resultCode){
                    Activity.RESULT_CANCELED->finish()

                    else->if(locationUtil.googleApiClient.isConnected){
                        locationUtil.getLocation()
                    }
                }
            }
        }
    }
}
