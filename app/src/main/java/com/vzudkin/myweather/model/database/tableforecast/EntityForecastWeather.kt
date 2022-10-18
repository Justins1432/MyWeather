package com.vzudkin.myweather.model.database.tableforecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_weather")
class EntityForecastWeather(
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "dateTxt")
    val dateTxt: String,
    @ColumnInfo(name = "main")
    val main: String,
    @ColumnInfo(name = "icon")
    val icon: String,
    @ColumnInfo(name = "temp_max")
    val tempMax: Double,
    @ColumnInfo(name = "temp_min")
    val tempMin: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}