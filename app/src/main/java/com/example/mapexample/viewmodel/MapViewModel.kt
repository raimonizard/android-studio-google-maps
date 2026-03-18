package com.example.mapexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapexample.model.MyMarker

class MapViewModel: ViewModel() {
    private val _markers = MutableLiveData(mutableListOf<MyMarker>())
    val markers = _markers

    private val _locationPermissionGranted = MutableLiveData(true)
    val locationPermissionGranted = _locationPermissionGranted

    private val _showSheet = MutableLiveData(false)
    val showSheet = _showSheet

    fun addMarker(marker: MyMarker) {
        _markers.value?.apply { add(marker) }
    }

    fun modifiyShowSheet() {
        _showSheet.value = !_showSheet.value!!
    }
}