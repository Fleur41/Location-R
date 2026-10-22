package com.sam.location_r.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.sam.location_r.debugLog
import com.sam.location_r.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class, com.google.accompanist.permissions.ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    fusedLocationClient: FusedLocationProviderClient
) {
    val context = LocalContext.current
    val coarseLocationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    val preciseLocationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var location by remember { mutableStateOf<MyLocation?>(null) }
    var address by remember { mutableStateOf<android.location.Address?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "LocationScreen") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val retrievedLocation = LocationUtility.getLocationFromLocationManager(context)
                    location = retrievedLocation
                    location?.let {
                        debugLog("Latitude: ${it.latitude}")
                        debugLog("Longitude: ${it.longitude}")

                        val retrievedAddress = LocationUtility.getAddressFromLocation(context, it)
                        address = retrievedAddress
                        address?.let{
                            debugLog("Address: $it")
                        }
                    }
                } else {
                    coarseLocationPermissionState.launchPermissionRequest()
                }

            }){
                 Text(text = "Get approximate (coarse) Screen")
            }

            Button(onClick = {
                LocationUtility.getLiveLocation(context){location ->
                    debugLog("Get live location: $location")
                }
            }) {
                Text(text = "Get Live Location")
            }

            Button(onClick = {
                if(preciseLocationPermissionState.status.isGranted){
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->
                            val myLocation = MyLocation(location.latitude, location.longitude)
                            debugLog("Got precise location: $myLocation")
                            debugLog("Latitude: ${myLocation.latitude}")
                            debugLog("Longitude: ${myLocation.longitude}")
                        }
                        .addOnFailureListener {
                            debugLog("Failed to get precise location")
                        }
                } else {
                    preciseLocationPermissionState.launchPermissionRequest()
                }
            }) {
                Text(text = "Get precise live Location")
            }

            AnimatedVisibility(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                visible = location != null
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Location Details", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Latitude: ${location?.latitude}")
                        Text(text = "Longitude: ${location?.longitude}")
                        VerticalSpacer(12)
                        Text(
                            text = "Address Details",
                            style = MaterialTheme.typography.titleMedium
                        )
                        address?.let {
                            Text(text = "Address: ${it.getAddressLine(0) ?: it}")
                        }
                    }
                }
            }
        }
    }
}