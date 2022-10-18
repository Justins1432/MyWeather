package com.vzudkin.myweather.model.database.tablecurrent

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CurrentWeatherDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entityCurrentWeather: EntityCurrentWeather)

    @Update
    suspend fun update(entityCurrentWeather: EntityCurrentWeather)

    @Delete
    suspend fun delete(entityCurrentWeather: EntityCurrentWeather)

    @Query("SELECT * FROM current_weather ORDER BY id ASC")
    fun getCurrentWeather(): LiveData<List<EntityCurrentWeather>>

    /*@Query("SELECT COUNT(*) FROM current_weather")
    fun getCount(): LiveData<Int>*/

    @Query("SELECT * FROM current_weather ORDER BY current_weather.id DESC")
    fun getLastAddedEntry(): LiveData<EntityCurrentWeather>

}