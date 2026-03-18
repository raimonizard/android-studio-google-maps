package com.example.mapexample

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.mapexample.ui.theme.MapExampleTheme
import com.example.mapexample.view.MapScreen
import com.example.mapexample.view.ShowMap
import com.example.mapexample.viewmodel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myViewModel by viewModels<MapViewModel>()
        setContent {
            MapExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val permissionState =
                        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                    LaunchedEffect(Unit){
                        permissionState.launchPermissionRequest()
                    }
                    if(permissionState.status.isGranted){
                        MapScreen(myViewModel)
                    }
                    else{
                        Text("Need permission")
                    }
                }
            }
        }
    }
}