package com.vzudkin.myweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vzudkin.myweather.model.network.FinalListWeather
import com.vzudkin.myweather.model.network.ReadListWeather
import com.vzudkin.myweather.util.Resource
import com.vzudkin.myweather.model.network.WeatherRepository
import kotlinx.coroutines.Dispatchers

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    fun getWeather(lat: Double, lon: Double) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getCurrentWeather(lat, lon)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error download data"))
        }
    }

    fun getForecast(city: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getForecastWeather(city)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error download data"))
        }
    }


}