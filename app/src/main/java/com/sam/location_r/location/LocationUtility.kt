package com.sam.location_r.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.Locale

data class MyLocation(
    val latitude: Double,
    val longitude: Double,
)
object LocationUtility {
    @SuppressLint("MissingPermission")
    fun getLocationFromLocationManager(context: Context): MyLocation? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        
        val hasFine = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) {
            return null
        }

        return try {
            val providers = listOf(
                LocationManager.GPS_PROVIDER,
                LocationManager.NETWORK_PROVIDER,
                LocationManager.PASSIVE_PROVIDER
            )
            var lastKnownLocation: Location? = null
            for (provider in providers) {
                if (locationManager.isProviderEnabled(provider)) {
                    val location = locationManager.getLastKnownLocation(provider)
                    if (location != null) {
                        lastKnownLocation = location
                        break
                    }
                }
            }

            if (lastKnownLocation != null) {
                MyLocation(
                    latitude = lastKnownLocation.latitude,
                    longitude = lastKnownLocation.longitude
                )
            } else {
                null
            }
        } catch (e: SecurityException) {
            null
        }
    }

    @SuppressLint("MissingPermission")
    fun getLiveLocation(
        context: Context,
        onLocationUpdateReceived: (MyLocation) -> Unit
    ){
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        
        val hasFine = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) {
            return
        }

        // Immediately provide last known location if available
        getLocationFromLocationManager(context)?.let {
            onLocationUpdateReceived(it)
        }

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val myLocation = MyLocation(latitude = location.latitude, longitude = location.longitude)
                onLocationUpdateReceived(myLocation)
            }
        }

        try {
            val providers = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
            for (provider in providers) {
                if (locationManager.isProviderEnabled(provider)) {
                    locationManager.requestLocationUpdates(
                        provider,
                        5000L,
                        0f,
                        locationListener,
                        android.os.Looper.getMainLooper()
                    )
                }
            }
        } catch (e: SecurityException) {
            // ignore
        }
    }

    fun getAddressFromLocation(context: Context, location: MyLocation): Address? {
        if (!Geocoder.isPresent()) {
            return null
        }
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            addresses?.firstOrNull()
        } catch (e: IOException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}