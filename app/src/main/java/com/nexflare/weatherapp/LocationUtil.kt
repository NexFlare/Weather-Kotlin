package com.nexflare.weatherapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

/**
 * Created by nexflare on 30/09/17.
 */

class LocationUtil(var context: Context, val locationChangedListener:LocationChangedListener ) : GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private var googleApiClient:GoogleApiClient
    private var locationRequest:LocationRequest

    init {
        googleApiClient=GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        googleApiClient.connect()
        locationRequest= LocationRequest.create().setInterval(5000)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        checkSettings()
    }

    private fun checkSettings() {
        Toast.makeText(context,"Check the app",Toast.LENGTH_SHORT).show()
    }

    override fun onConnected(p0: Bundle?) {
        locationChangedListener.onLocationChanged(27.32,45.11)
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
    }

}
