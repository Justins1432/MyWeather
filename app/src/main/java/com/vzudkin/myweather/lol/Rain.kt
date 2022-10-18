package com.vzudkin.myweather.lol

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h1: Double
)