package com.vzudkin.myweather.model.data.revweather

data class ReverseGeoItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)