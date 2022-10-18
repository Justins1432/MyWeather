package com.vzudkin.myweather.model.network

import com.vzudkin.myweather.model.data.MyList

class FinalListWeather {

    fun finalList(list: ArrayList<MyList>): ArrayList<ArrayList<MyList>> {
        val listListWeather: ArrayList<ArrayList<MyList>> = arrayListOf()
        var bufferList: ArrayList<MyList> = arrayListOf()

        if (list.size > 1) {
            list.forEachIndexed { index, weather ->
                when (index != list.size - 1) {
                    true -> {
                        if (weather.dt_txt_parse == list[index + 1].dt_txt_parse){
                            bufferList.add(weather)
                        } else if (index != 0 && weather.dt_txt_parse == list[index - 1].dt_txt_parse) {
                            bufferList.add(weather)
                            listListWeather.add(bufferList)
                            bufferList = arrayListOf()
                        } else {
                            bufferList.add(weather)
                            listListWeather.add(bufferList)
                            bufferList = arrayListOf()
                        }
                    }
                    false -> {
                        if (weather.dt_txt_parse == list[index - 1].dt_txt_parse) {
                            bufferList.add(weather)
                            listListWeather.add(bufferList)
                        } else {
                            bufferList = arrayListOf()
                            bufferList.add(weather)
                            listListWeather.add(bufferList)
                        }
                    }
                }
            }
        } else {
            listListWeather.add(list)
        }
        return listListWeather
    }


}