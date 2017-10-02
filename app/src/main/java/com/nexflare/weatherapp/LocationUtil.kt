package com.nexflare.weatherapp

import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*

/**
 * Created by nexflare on 30/09/17.
 */

class LocationUtil(var context: Activity, val locationChangedListener: LocationChangedListener) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    var googleApiClient: GoogleApiClient
    private var locationRequest: LocationRequest
    private var REQUEST_CHECK_SETTINGS=0x1

    init {
        googleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        googleApiClient.connect()
        locationRequest = LocationRequest.create().setInterval(5000)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        checkLocationSettings()
    }


    private fun checkLocationSettings() {
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true) //this is the key ingredient

        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> if (!googleApiClient.isConnected) {
                    googleApiClient.connect()
                } else {
                    getLocation()
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->

                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(context, REQUEST_CHECK_SETTINGS)
                    } catch (e: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
            }// All location settings are satisfied. The client can initialize location
            // Location settings are not satisfied. However, we have no way to fix the
            // settings so we won't show the dialog.
        }

    }

    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest) {
            location ->
            locationChangedListener.onLocationChanged(location.latitude, location.longitude)
        }

    }

    override fun onConnected(p0: Bundle?) {
        getLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

}
