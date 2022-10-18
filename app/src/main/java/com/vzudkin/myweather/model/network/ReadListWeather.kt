package com.vzudkin.myweather.model.network

import ReadyModelWeather
import com.vzudkin.myweather.model.data.MyList

class ReadListWeather {
    fun readList(listValue: ArrayList<ArrayList<MyList>>): ArrayList<ReadyModelWeather> {
        val readList = arrayListOf<ReadyModelWeather>()
        listValue.forEachIndexed { index, modelWeathers ->
            val date = modelWeathers[0].dt_txt_parse
            var maxValue = modelWeathers[0].main.tempMax
            var minValue = modelWeathers[0].main.tempMin
            val icon = modelWeathers[0].weather[0].icon
            var iterable = 1

            modelWeathers.forEachIndexed { index, currentModelWeather ->
                if (index != 0) {
                    iterable++
                    maxValue += currentModelWeather.main.tempMax
                    minValue += currentModelWeather.main.tempMin
                }
            }
            readList.add(ReadyModelWeather(date, averageTemp(maxValue, iterable), averageTemp(minValue, iterable), icon))
        }
        return readList
    }

    private fun averageTemp(result: Double, divider: Int) = result / divider
}