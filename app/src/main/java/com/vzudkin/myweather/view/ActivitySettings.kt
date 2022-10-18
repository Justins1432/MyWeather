package com.vzudkin.myweather.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vzudkin.myweather.R
import com.vzudkin.myweather.model.data.DirectGeocoding
import com.vzudkin.myweather.model.data.revweather.ReverseGeoItem
import com.vzudkin.myweather.model.network.ApiHelper
import com.vzudkin.myweather.model.network.NetworkService
import com.vzudkin.myweather.util.Constants
import com.vzudkin.myweather.util.Status
import com.vzudkin.myweather.view.adapter.ClickCity
import com.vzudkin.myweather.view.adapter.SearchCityAdapter
import com.vzudkin.myweather.viewmodel.GeocodingViewModel
import com.vzudkin.myweather.viewmodel.WeatherViewModelFactory
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.util.concurrent.TimeUnit

private const val TAG_SETTINGS = "ActivitySettings"

private interface Geolocation {
    fun enableLocation(reverseGeoItem: ReverseGeoItem)
}

@Suppress("DEPRECATION")
class ActivitySettings : AppCompatActivity(), ClickCity {
    private lateinit var imgGetBack: ImageView
    private lateinit var switchCompat: SwitchCompat
    private lateinit var viewModelGeo: GeocodingViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var textView: TextView

    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    private val searchCityAdapter = SearchCityAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initView()
        fetchLocation()
        getBack()
        setupViewModel()
        initRecyclerView()
        searchCity()
    }

    private fun initView() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title)
        supportActionBar?.hide()
        imgGetBack = findViewById(R.id.backImage)
        switchCompat = findViewById(R.id.activeGeo)
        recyclerView = findViewById(R.id.recycler_view_cities_list)
        searchView = findViewById(R.id.searchCityView)
        textView = findViewById(R.id.txtPlace)
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        activateGeolocation()
    }

    private fun fetchLocation() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.COORDINATES, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val task = fusedLocationProvider.lastLocation

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                val lat = it.latitude;
                val lon = it.longitude
                viewModelGeo.getCityGeolocation(lat, lon).observe(this) { it ->
                    val country = it.data?.get(0)?.country
                    val name = it.data?.get(0)?.name
                    val city = "$name,$country"
                    editor.apply {
                        putLong(Constants.LATITUDE, lat.toBits())
                        putLong(Constants.LONGITUDE, lon.toBits())
                        putString(Constants.CITY, city)
                    }.apply()
                    Log.d(TAG_SETTINGS, "Coordinates: $lat, $lon: City: $city")
                }

            }
        }
    }

    private fun activateGeolocation() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.COORDINATES, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        switchCompat.setOnClickListener {
            if (switchCompat.isChecked) {
                editor.apply {
                    putBoolean(Constants.SWITCH_STATUS, true)
                    sharedPreferences.getString(Constants.CITY, null)
                    sharedPreferences.getLong(Constants.LATITUDE, 1)
                    sharedPreferences.getLong(Constants.LONGITUDE, 1)
                }.apply()
                switchCompat.isChecked = true
            } else {
                editor.apply {
                    putBoolean(Constants.SWITCH_STATUS, false)
                }.apply()
                sharedPreferences.getString(Constants.CITY, null)
                sharedPreferences.getLong(Constants.LATITUDE, 1)
                sharedPreferences.getLong(Constants.LONGITUDE, 1)
                editor.clear()
                editor.apply()
                switchCompat.isChecked = false
            }
        }

    }

    private fun getBack() {
        imgGetBack.setOnClickListener {
            finish()
        }
    }

    private fun setupViewModel() {
        viewModelGeo = ViewModelProviders.of(
            this,
            WeatherViewModelFactory(ApiHelper(NetworkService.api))
        )[GeocodingViewModel::class.java]
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //searchCityAdapter = SearchCityAdapter()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = searchCityAdapter
    }

    @SuppressLint("CheckResult")
    private fun searchCity() {
        Observable.create(ObservableOnSubscribe<String> { subscribe ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscribe.onNext(query!!)
                    return false
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    subscribe.onNext(text!!)
                    if (text.trim().isNotEmpty()) {
                        viewModelGeo.getListCities(text).observe(this@ActivitySettings) {
                            it?.let { resource ->
                                when (resource.status) {
                                    Status.SUCCESS -> {
                                        resource.data?.let { it ->
                                            searchCityAdapter.filter.filter(text)
                                            retrieveListCities(it)
                                        }
                                    }
                                    Status.LOADING -> {

                                    }
                                    Status.ERROR -> {
                                        Toast.makeText(
                                            this@ActivitySettings,
                                            it.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    } else if (text.isEmpty())
                        searchCityAdapter.clearList()
                    else {
                        clearList()
                    }
                    return false
                }
            })
        }).debounce(500, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
            .subscribe()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveListCities(list: ArrayList<DirectGeocoding>) {
        searchCityAdapter.apply {
            searchCity(list)
            notifyDataSetChanged()
        }
    }

    private fun clearList() {
        searchCityAdapter.apply {
            clearList()
        }
    }

    override fun onItemClickCity(directGeocoding: DirectGeocoding) {
        val intent = Intent(this@ActivitySettings, MainActivity::class.java)
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.COORDINATES, MODE_PRIVATE)

        val city = directGeocoding.name;
        val country = directGeocoding.country
        val cityAndCountry = "$city,$country"

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply {
            putString(Constants.CITY, cityAndCountry)
            putLong(Constants.LATITUDE, directGeocoding.lat.toBits())
            putLong(Constants.LONGITUDE, directGeocoding.lon.toBits())
        }.apply()
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.COORDINATES, MODE_PRIVATE)
        switchCompat.isChecked = sharedPreferences.getBoolean(Constants.SWITCH_STATUS, false)
    }
}