package com.vzudkin.myweather.view

import ReadyModelWeather
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.vzudkin.myweather.util.Status
import com.vzudkin.myweather.R
import com.vzudkin.myweather.model.network.ApiHelper
import com.vzudkin.myweather.model.network.FinalListWeather
import com.vzudkin.myweather.model.network.NetworkService
import com.vzudkin.myweather.model.network.ReadListWeather
import com.vzudkin.myweather.util.Constants
import com.vzudkin.myweather.util.URL_ICON
import com.vzudkin.myweather.view.adapter.ForecastWeatherAdapter
import com.vzudkin.myweather.viewmodel.DatabaseWeatherViewModel
import com.vzudkin.myweather.viewmodel.WeatherViewModel
import com.vzudkin.myweather.viewmodel.WeatherViewModelFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.collections.ArrayList

private const val TAG_ACTIVITY_MAIN = "activity_main"

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var viewModelDatabaseWeatherViewModel: DatabaseWeatherViewModel
    private lateinit var txtCity: TextView
    private lateinit var txtTemp: TextView
    private lateinit var txtDescription: TextView
    private lateinit var imageViewSettings: ImageView
    private lateinit var imgWeatherIcon: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterForecast: ForecastWeatherAdapter
    private lateinit var shimmerForecast: ShimmerFrameLayout

    private lateinit var txtCityTest: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        goSetting()
        setupViewModel()
        getCurrentWeather()
        initRecyclerView()
        getForecastWeather()
    }

    private fun initView() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title)
        supportActionBar?.hide()
        txtCity = findViewById(R.id.city)
        txtTemp = findViewById(R.id.tempCity)
        txtDescription = findViewById(R.id.weatherDescription)
        imgWeatherIcon = findViewById(R.id.imgWeather)
        imageViewSettings = findViewById(R.id.goSettings)
        recyclerView = findViewById(R.id.forecast_recycler_view)
        shimmerForecast = findViewById(R.id.shimmerRecyclerView)
        shimmerForecast.startShimmer()
        txtCityTest = findViewById(R.id.cityAndCountry)
    }

    private fun goSetting() {
        imageViewSettings.setOnClickListener {
            val intent = Intent(this, ActivitySettings::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            WeatherViewModelFactory(ApiHelper(NetworkService.api))
        )[WeatherViewModel::class.java]

        viewModelDatabaseWeatherViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DatabaseWeatherViewModel::class.java]
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterForecast = ForecastWeatherAdapter()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapterForecast
    }

    private fun getCurrentWeather() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.COORDINATES, MODE_PRIVATE)
        val latDouble: Double = Double.fromBits(sharedPreferences.getLong(Constants.LATITUDE, 1))
        val lonDouble: Double = Double.fromBits(sharedPreferences.getLong(Constants.LONGITUDE, 1))

        /*viewModelDatabaseWeatherViewModel.allCurrentWeather.observe(this, Observer { it ->
            txtCity.text = it[0].city
            txtTemp.text = it[0].temp.toString()
            txtDescription.text = it[0].description

        })*/

        viewModelDatabaseWeatherViewModel.getLastAddedEntry().observe(this, Observer {
            txtCity.text = it.city
            txtTemp.text = it.temp.toString()
            txtDescription.text = it.description
        })


        /*viewModel.getWeather(latDouble, lonDouble).observe(this, Observer {
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        resources.data?.let { it ->
                            val description = it.weather[0].description
                            val iconWeather = it.weather[0].icon
                            val iconImg = "$URL_ICON$iconWeather.png"
                            val temp = it.main.temp
                            val format: String = DecimalFormat("#0").format(temp)
                            txtCity.text = it.name
                            txtTemp.text = format
                            txtDescription.text = description
                            Glide.with(this@MainActivity)
                                .asBitmap()
                                .load(iconImg)
                                .into(imgWeatherIcon)
                        }
                    }
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        Log.e("error", "${it.message}")
                    }
                }
            }
        })*/
    }

    @SuppressLint("SimpleDateFormat")
    private fun getForecastWeather() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.COORDINATES, MODE_PRIVATE)
        val city = sharedPreferences.getString(Constants.CITY, null)
        val cityString: String = city.toString()

       /* viewModel.getForecast(cityString).observe(this, Observer { it ->
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        shimmerForecast.stopShimmer()
                        shimmerForecast.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        resources.data?.let {
                            retrieveForecastWeather(it)
                        }
                    }
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        Log.e("error", "${it.message}")
                    }
                }
            }
        })*/
    }

    private fun retrieveForecastWeather(list: ArrayList<ReadyModelWeather>) {
        this.adapterForecast.apply {
            addListWeather(list)
            notifyDataSetChanged()
        }
    }

}