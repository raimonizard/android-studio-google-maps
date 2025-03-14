package com.example.mapexample

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMarkerScreen(myViewModel: MapViewModel, coordinates: LatLng, onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Add Marker", fontSize = 16.sp, modifier = Modifier.padding(16.dp)
            )
            TextField(value = "", onValueChange = {}, placeholder = { Text("Title") })
            Button(onClick = {
                val newMarker = MyMarker(
                    "Marker${myViewModel.markers.value!!.size + 1}",
                    "New Marker",
                    coordinates.latitude,
                    coordinates.longitude
                )
                myViewModel.addMarker(newMarker)
                Log.i("Marker added", "${myViewModel.markers.value!!.size}")
                onDismiss()
            }) {
                Text(text = "Add Marker")
            }
        }

    }
}