package com.vzudkin.myweather.model.network

import com.vzudkin.myweather.lol.Root
import com.vzudkin.myweather.model.data.DirectGeocoding
import com.vzudkin.myweather.model.data.ForecastWeather
import com.vzudkin.myweather.model.data.revweather.ReverseGeo
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("/data/2.5/weather?&appid=a552b39b529b0402a3b40d2affee9ef4&units=metric&lang=ru")
    suspend fun currentWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Root

    @GET("/data/2.5/forecast?&appid=a552b39b529b0402a3b40d2affee9ef4&units=metric&lang=ru&cnt=40")
    suspend fun forecastWeather(@Query("q") city: String): ForecastWeather

    @GET("/geo/1.0/reverse?&limit=5&appid=a552b39b529b0402a3b40d2affee9ef4")
    suspend fun reverseGeocoding(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): ReverseGeo

    @GET("/geo/1.0/direct?&limit=5&appid=a552b39b529b0402a3b40d2affee9ef4")
    suspend fun directCoding(@Query("q") city: String): ArrayList<DirectGeocoding>

}