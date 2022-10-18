package com.vzudkin.myweather.di

import com.vzudkin.myweather.di.Car
import com.vzudkin.myweather.di.Engine
import com.vzudkin.myweather.di.Fuel
import dagger.Component

@Component
interface DaggerComponent {
    fun getCar(): Car
    fun getEngine(): Engine
    fun getFuel(): Fuel
}