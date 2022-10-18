package com.vzudkin.myweather.model.database.tablecurrent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class EntityCurrentWeather(
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "icon")
    val icon: String,
    @ColumnInfo(name = "main")
    val main: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "temperature")
    val temp: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
