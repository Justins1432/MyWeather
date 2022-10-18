package com.vzudkin.myweather.model.network

import ReadyModelWeather
import com.vzudkin.myweather.lol.Root
import com.vzudkin.myweather.model.data.DirectGeocoding
import com.vzudkin.myweather.model.data.ForecastWeather
import com.vzudkin.myweather.model.data.revweather.ReverseGeo
import com.vzudkin.myweather.model.database.tableforecast.EntityForecastWeather

class WeatherRepository(private val apiHelper: ApiHelper) {
    suspend fun getCurrentWeather(lat: Double, lon: Double): Root =
        apiHelper.getCurrentWeather(lat, lon)

    suspend fun getForecastWeather(city: String): ArrayList<ReadyModelWeather> {
        val forecast = apiHelper.getForecastWeather(city)
        val list = forecast.list
        val finalListWeather = FinalListWeather().finalList(list)
        return ReadListWeather().readList(finalListWeather)
    }

    suspend fun getDirectGeocoding(city: String): ArrayList<DirectGeocoding> {
        return apiHelper.getDirectCoding(city)
    }

    suspend fun getReverseGeocoding(lat: Double, lon: Double): ReverseGeo {
        return apiHelper.getReverseCoding(lat, lon)
    }

}