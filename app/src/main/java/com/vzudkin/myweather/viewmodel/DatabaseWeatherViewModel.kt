package com.vzudkin.myweather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vzudkin.myweather.app.MyApplication
import com.vzudkin.myweather.model.database.CurrentWeatherRepository
import com.vzudkin.myweather.model.database.ForecastWeatherRepository
import com.vzudkin.myweather.model.database.tablecurrent.EntityCurrentWeather
import com.vzudkin.myweather.model.database.tableforecast.EntityForecastWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DatabaseWeatherViewModel(application: Application) : AndroidViewModel(application) {

    var allCurrentWeather: LiveData<List<EntityCurrentWeather>>
        private set
    val repositoryCurrentWeather: CurrentWeatherRepository

    var allForecastWeather: LiveData<List<EntityForecastWeather>>
        private set
    val repositoryForecastWeather: ForecastWeatherRepository


    init {
        val dao = MyApplication.weatherDatabase
        repositoryCurrentWeather = CurrentWeatherRepository(dao.getCurrentWeatherDAO())
        repositoryForecastWeather = ForecastWeatherRepository(dao.getForecastWeatherDAO())
        allCurrentWeather = repositoryCurrentWeather.allCurrentWeather
        allForecastWeather = repositoryForecastWeather.allForecastWeather
    }

    /*fun getCount(): LiveData<Int> {
        return repositoryCurrentWeather.getCount()
    }*/

    fun getLastAddedEntry(): LiveData<EntityCurrentWeather> {
        return repositoryCurrentWeather.getLastAddedEntry()
    }

    fun addCurrentWeather(entityCurrentWeather: EntityCurrentWeather) =
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCurrentWeather.insert(entityCurrentWeather)
        }

    fun addForecastWeather(entityForecastWeather: EntityForecastWeather) =
        viewModelScope.launch(Dispatchers.IO) {
            repositoryForecastWeather.insert(entityForecastWeather)
        }

    fun updateCurrentWeather(entityCurrentWeather: EntityCurrentWeather) =
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCurrentWeather.update(entityCurrentWeather)
        }

    fun updateForecastWeather(entityForecastWeather: EntityForecastWeather) =
        viewModelScope.launch(Dispatchers.IO) {
            repositoryForecastWeather.update(entityForecastWeather)
        }

    fun deleteCurrentWeather(entityCurrentWeather: EntityCurrentWeather) =
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCurrentWeather.delete(entityCurrentWeather)
        }

    fun deleteForecastWeather(entityForecastWeather: EntityForecastWeather) =
        viewModelScope.launch(Dispatchers.IO) {
            repositoryForecastWeather.delete(entityForecastWeather)
        }

}