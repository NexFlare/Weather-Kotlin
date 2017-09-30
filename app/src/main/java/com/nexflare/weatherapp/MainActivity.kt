package com.nexflare.weatherapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val REQUEST_LOCATION: Int = 2309
    private var REQUEST_CHECK_SETTINGS=0x1
    lateinit var locationUtil: LocationUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkForPermission()


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
        locationUtil = LocationUtil(this@MainActivity, object : LocationChangedListener {
            override fun onLocationChanged(latitute: Double?, longitude: Double?) {
                Toast.makeText(this@MainActivity,""+latitute+" "+longitude,Toast.LENGTH_SHORT).show()
                locationUtil.googleApiClient.disconnect()
            }
        }

        )

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
