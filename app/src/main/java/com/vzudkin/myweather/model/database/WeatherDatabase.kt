package com.vzudkin.myweather.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vzudkin.myweather.model.database.tablecurrent.CurrentWeatherDAO
import com.vzudkin.myweather.model.database.tablecurrent.EntityCurrentWeather
import com.vzudkin.myweather.model.database.tableforecast.EntityForecastWeather
import com.vzudkin.myweather.model.database.tableforecast.ForecastWeatherDAO

@Database(
    entities = [EntityCurrentWeather::class, EntityForecastWeather::class],
    version = 1, exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun getCurrentWeatherDAO(): CurrentWeatherDAO
    abstract fun getForecastWeatherDAO(): ForecastWeatherDAO

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
    }
}