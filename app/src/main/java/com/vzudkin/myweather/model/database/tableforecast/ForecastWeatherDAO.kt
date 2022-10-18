package com.vzudkin.myweather.model.database.tableforecast

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForecastWeatherDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entityForecastWeather: EntityForecastWeather)

    @Update
    suspend fun update(entityForecastWeather: EntityForecastWeather)

    @Delete
    suspend fun delete(entityForecastWeather: EntityForecastWeather)

    @Query("SELECT * FROM forecast_weather ORDER BY id ASC")
    fun getForecastWeather(): LiveData<List<EntityForecastWeather>>
}