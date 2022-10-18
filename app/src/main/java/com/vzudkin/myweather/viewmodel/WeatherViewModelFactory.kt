package com.vzudkin.myweather.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vzudkin.myweather.model.network.ApiHelper
import com.vzudkin.myweather.model.network.WeatherRepository

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory(
    val apiHelper: ApiHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(WeatherRepository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(GeocodingViewModel::class.java)) {
            return GeocodingViewModel(WeatherRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}