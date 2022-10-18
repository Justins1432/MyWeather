package com.vzudkin.myweather.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vzudkin.myweather.R
import com.vzudkin.myweather.model.database.tablecurrent.EntityCurrentWeather
import com.vzudkin.myweather.model.network.ApiHelper
import com.vzudkin.myweather.model.network.NetworkService
import com.vzudkin.myweather.util.Constants
import com.vzudkin.myweather.util.URL_ICON
import com.vzudkin.myweather.viewmodel.DatabaseWeatherViewModel
import com.vzudkin.myweather.viewmodel.GeocodingViewModel
import com.vzudkin.myweather.viewmodel.WeatherViewModel
import com.vzudkin.myweather.viewmodel.WeatherViewModelFactory
import kotlinx.coroutines.Job

@Suppress("DEPRECATION")
class SplashProgressActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: DatabaseWeatherViewModel
    private lateinit var viewModelWeatherViewModel: WeatherViewModel
    private lateinit var viewModelGeocoding: GeocodingViewModel
    private lateinit var txtStatus: TextView
    private var handler = Handler()

    private var progressStatus = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_progress)
        initView()
        setupViewModel()
        downloadDataCurrentWeather()
        downloadDataForecastWeather()
        splashScreen()
    }

    private fun initView() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title)
        supportActionBar?.hide()
        progressBar = findViewById(R.id.progressBar)
        txtStatus = findViewById(R.id.txtStatus)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DatabaseWeatherViewModel::class.java]

        viewModelWeatherViewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(ApiHelper(NetworkService.api))
        )[WeatherViewModel::class.java]

        viewModelGeocoding = ViewModelProvider(
            this,
            WeatherViewModelFactory(ApiHelper(NetworkService.api))
        )[GeocodingViewModel::class.java]
    }

    private fun downloadDataCurrentWeather() {
        val idCurrentWeather: Long = 0
        var city: String
        var lat: Double = 0.0
        var lon: Double = 0.0
        var icon: String
        var main: String
        var description: String
        var temp: Double = 0.0

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.COORDINATES, MODE_PRIVATE)
        val latDouble: Double = Double.fromBits(sharedPreferences.getLong(Constants.LATITUDE, 1))
        val lonDouble: Double = Double.fromBits(sharedPreferences.getLong(Constants.LONGITUDE, 1))

        /*viewModelWeatherViewModel.getWeather(latDouble, lonDouble).observe(this, Observer { it ->
            it?.let { resource ->
                resource.data?.let { it ->
                    val iconPng = it.weather[0].icon
                    val iconImg = "$URL_ICON$iconPng.png"

                    city = it.name
                    lat = latDouble
                    lon = lonDouble
                    icon = iconImg
                    main = it.weather[0].main
                    description = it.weather[0].description
                    temp = it.main.temp

                    val entityCurrentWeather =
                        EntityCurrentWeather(city, lat, lon, icon, main, description, temp)
                    entityCurrentWeather.id = idCurrentWeather
                    viewModel.addCurrentWeather(entityCurrentWeather)

                    /*viewModel.getCount().observe(this) {
                        if (it == 0) {
                            viewModel.addCurrentWeather(entityCurrentWeather)
                        } else {
                            viewModel.updateCurrentWeather(entityCurrentWeather)
                        }

                    }*/

                }
            }
        })*/

    }

    private fun downloadDataForecastWeather() {
        val idForecastWeather: Long = 0
        var cityGeo: String
        var dateTxt: String
        var lat: Double = 0.0
        var lon: Double = 0.0
        var icon: String
        var main: String
        var tempMax: Double = 0.0
        var tempMin: Double = 0.0

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.COORDINATES, MODE_PRIVATE)
        val city = sharedPreferences.getString(Constants.CITY, null)
        val cityString: String = city.toString()


    }

    @SuppressLint("SetTextI18n")
    private fun splashScreen() {
        /**
         * Во время загрузки идет запрос на получение прогноза погоды
         */
        /*progressBar.max = 2
        val cProgress = 2
        ObjectAnimator.ofInt(progressBar, "progress", cProgress)
            .setDuration(2000).start()
        Handler().postDelayed({
            val intent = Intent(this@SplashProgressActivity, MainActivity::class.java)
            startActivity(intent)
        }, 3000)*/
        //размер данных
        val dataSize = 17
        //задаем верхнюю границу диапазона размер файла(ов)/данных поступающих с сервера
        progressBar.max = dataSize

        /*Thread {
            while (progressStatus < dataSize) {
                progressStatus += 1
                Thread.sleep(100)
                Handler().post {
                    progressBar.progress = progressStatus
                    val percentage = (progressStatus.toDouble() / dataSize * 100).toInt()
                    txtStatus.text = "Загружено $progressStatus ($percentage%)"
                    if (progressStatus == dataSize) {
                        Toast.makeText(this, "Данные загружены", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }.start()*/

        Thread(Runnable {
            while (progressStatus < dataSize) {
                progressStatus += 1
                Thread.sleep(100)
                handler.post {
                    progressBar.progress = progressStatus
                    val percentage = (progressStatus.toDouble() / dataSize * 100).toInt()
                    txtStatus.text = "Загружено $progressStatus ($percentage%)"
                    if (progressStatus == dataSize) {
                        //val intent = Intent(this, MainActivity::class.java)
                        //startActivity(intent)
                    }
                }
            }
        }
        ).start()
    }

}