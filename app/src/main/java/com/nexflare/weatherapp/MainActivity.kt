package com.nexflare.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val REQUEST_LOCATION:Int=2309
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkForPermission()


    }



    fun checkForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_LOCATION)
            }
            else
                getLocation()
        }
    }

    fun getLocation(){
        val locationUtil=LocationUtil(this@MainActivity,object:LocationChangedListener{
            override fun onLocationChanged(latitute: Double?, longitude: Double?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        }

        )

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_LOCATION->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getLocation()
                }
            }
        }
    }
}
