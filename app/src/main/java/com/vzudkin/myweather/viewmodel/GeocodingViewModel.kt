package com.vzudkin.myweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vzudkin.myweather.model.network.WeatherRepository
import com.vzudkin.myweather.util.Resource
import kotlinx.coroutines.Dispatchers

class GeocodingViewModel(private val repository: WeatherRepository) : ViewModel() {

    fun getListCities(city: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getDirectGeocoding(city)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Error download data"))
        }
    }

    fun getCityGeolocation(lat: Double, lon: Double) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getReverseGeocoding(lat, lon)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Error download data"))
        }
    }

}