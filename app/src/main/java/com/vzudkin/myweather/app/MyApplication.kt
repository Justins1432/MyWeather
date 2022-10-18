package com.vzudkin.myweather.app

import android.app.Application
import androidx.room.Room
import com.vzudkin.myweather.model.database.WeatherDatabase
import com.vzudkin.myweather.model.network.API
import com.vzudkin.myweather.model.network.NetworkService


class MyApplication : Application() {
    companion object {
        lateinit var weatherDatabase: WeatherDatabase
        lateinit var api: API
    }

    override fun onCreate() {
        super.onCreate()
        api = NetworkService.api
        weatherDatabase =
            Room.databaseBuilder(this, WeatherDatabase::class.java, "db_weather").build()
    }

}