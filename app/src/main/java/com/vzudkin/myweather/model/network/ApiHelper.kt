package com.vzudkin.myweather.model.network

import com.vzudkin.myweather.lol.Root
import com.vzudkin.myweather.model.data.ForecastWeather
import com.vzudkin.myweather.model.data.DirectGeocoding
import com.vzudkin.myweather.model.data.revweather.ReverseGeo

class ApiHelper(private val api: API) {
    suspend fun getCurrentWeather(lat: Double, lon: Double): Root = api.currentWeather(lat, lon)

    suspend fun getForecastWeather(city: String): ForecastWeather = api.forecastWeather(city)

    suspend fun getDirectCoding(city: String): ArrayList<DirectGeocoding> {
        return api.directCoding(city)
    }

    suspend fun getReverseCoding(lat: Double, lon: Double): ReverseGeo {
        return api.reverseGeocoding(lat, lon)
    }

}