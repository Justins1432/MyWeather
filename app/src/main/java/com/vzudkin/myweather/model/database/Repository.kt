package com.vzudkin.myweather.model.database

import android.provider.ContactsContract.CommonDataKinds.Callable
import androidx.lifecycle.LiveData
import com.vzudkin.myweather.model.database.tablecurrent.CurrentWeatherDAO
import com.vzudkin.myweather.model.database.tablecurrent.EntityCurrentWeather
import com.vzudkin.myweather.model.database.tableforecast.EntityForecastWeather
import com.vzudkin.myweather.model.database.tableforecast.ForecastWeatherDAO

class CurrentWeatherRepository(private val currentWeatherDAO: CurrentWeatherDAO) {
    val allCurrentWeather: LiveData<List<EntityCurrentWeather>> =
        currentWeatherDAO.getCurrentWeather()

    /*fun getCount(): LiveData<Int> {
        return currentWeatherDAO.getCount()
    }*/

    fun getLastAddedEntry(): LiveData<EntityCurrentWeather> {
        return currentWeatherDAO.getLastAddedEntry()
    }

    suspend fun insert(entityCurrentWeather: EntityCurrentWeather) {
        currentWeatherDAO.insert(entityCurrentWeather)
    }

    suspend fun update(entityCurrentWeather: EntityCurrentWeather) {
        currentWeatherDAO.update(entityCurrentWeather)
    }

    suspend fun delete(entityCurrentWeather: EntityCurrentWeather) {
        currentWeatherDAO.delete(entityCurrentWeather)
    }

}

class ForecastWeatherRepository(private val forecastWeatherDAO: ForecastWeatherDAO) {
    val allForecastWeather: LiveData<List<EntityForecastWeather>> =
        forecastWeatherDAO.getForecastWeather()

    suspend fun insert(entityForecastWeather: EntityForecastWeather) {
        forecastWeatherDAO.insert(entityForecastWeather)
    }

    suspend fun update(entityForecastWeather: EntityForecastWeather) {
        forecastWeatherDAO.update(entityForecastWeather)
    }

    suspend fun delete(entityForecastWeather: EntityForecastWeather) {
        forecastWeatherDAO.delete(entityForecastWeather)
    }

}