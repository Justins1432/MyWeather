package com.vzudkin.myweather.model.data

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

interface DateParse {
    val dt_txt: String
    val dt_txt_parse: String
        get() = dateParse(dt_txt)

    @SuppressLint("SimpleDateFormat")
    private fun dateParse(dt_txt: String): String {
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date: Date = simpleDateFormat.parse(dt_txt) as Date
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        return simpleDateFormat.format(date)
    }
}

data class ForecastWeather(
    @SerializedName("cod") val cod: String,
    @SerializedName("message") val message: Int,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val list: ArrayList<MyList>,
    @SerializedName("city") val city: City
)

data class City(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("coord") val coord: CoordCity,
    @SerializedName("country") val country: String,
    @SerializedName("population") val population: Int,
    @SerializedName("timezone") val timezone: Int,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int
)

data class CloudsForecastWeather(
    @SerializedName("all") val all: Int
)

data class CoordCity(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)

data class MyList(
    @SerializedName("dt") val dt: Int,
    @SerializedName("main") val main: MainForecastWeather,
    @SerializedName("weather") val weather: ArrayList<WeatherForecast>,
    @SerializedName("clouds") val cloudsForecastWeather: CloudsForecastWeather,
    @SerializedName("wind") val wind: WindForecastWeather,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("pop") val pop: Double,
    @SerializedName("sys") val sys: SysForecastWeather,
    @SerializedName("dt_txt") override val dt_txt: String
) : DateParse

data class MainForecastWeather(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("sea_level") val seaLevel: Int,
    @SerializedName("grnd_level") val grndLevel: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("temp_kf") val tempKf: Double
)

data class SysForecastWeather(
    @SerializedName("pod") val pod: String
)

data class WeatherForecast(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class WindForecastWeather(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Int,
    @SerializedName("gust") val gust: Double
)