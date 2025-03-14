package com.example.mapexample

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(myViewModel: MapViewModel) {
    val hasPermission by myViewModel.locationPermissionGranted.observeAsState(false)
    if (hasPermission) {
        ShowMap(myViewModel)
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Need permission")
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun ShowMap(myViewModel: MapViewModel) {
    val context = LocalContext.current
    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }
    var lastKnownLocation by remember { mutableStateOf<Location?>(null) }
    var deviceLatLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val cameraPositionState =
        rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f) }
    val locationResult = fusedLocationProviderClient.getCurrentLocation(100, null)

    var coordinates by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val showSheet by myViewModel.showSheet.observeAsState(false)
    val markersList by myViewModel.markers.observeAsState(mutableListOf())

    locationResult.addOnCompleteListener(context as MainActivity) { task ->
        if (task.isSuccessful) {
            lastKnownLocation = task.result
            deviceLatLng = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
        } else {
            Log.d("Hello", "Current location is null. Using defaults.")
            Log.e("Hello", "Exception: %s", task.exception)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState, onMapLongClick = {
                myViewModel.modifiyShowSheet()
                coordinates = it
            }
        ) {
            Marker(
                state = MarkerState(position = deviceLatLng),
                title = "You"
            )
            markersList.forEach {
                Marker(
                    state = MarkerState(position = LatLng(it.lat, it.lon)),
                    title = it.title,
                    snippet = it.snippet
                )
            }
        }
        if (showSheet == true) {
            AddMarkerScreen(myViewModel, coordinates) { myViewModel.modifiyShowSheet() }
        }
    }
}

