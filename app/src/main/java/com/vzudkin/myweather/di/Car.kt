package com.vzudkin.myweather.di

import javax.inject.Inject

class Car @Inject constructor(private val engine: Engine) {
    fun startBenzineEngine() {
        engine.startBenzineEngine()
    }
    fun startDieselEngine(){
        engine.startDieselEngine()
    }
}

class Engine @Inject constructor(private var fuel: Fuel) {
    fun startBenzineEngine() {
        println("Двигатель заправлен ${fuel.benzine}")
    }

    fun startDieselEngine() {
        println("Двигатель заправлен ${fuel.diesel}")
    }
}

class Fuel @Inject constructor() {
    val benzine = "Бензин"
    val diesel = "ДТ"
}