package com.vzudkin.myweather.view.adapter

import ReadyModelWeather
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vzudkin.myweather.R
import com.vzudkin.myweather.model.data.MyList
import com.vzudkin.myweather.model.network.ReadListWeather
import com.vzudkin.myweather.util.URL_ICON
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ForecastWeatherAdapter : RecyclerView.Adapter<ForecastWeatherAdapter.ForecastViewHolder>() {
    private lateinit var simpleDateFormat: SimpleDateFormat
    private val calendar: Calendar = Calendar.getInstance()
    private var listForecast = ArrayList<ReadyModelWeather>()


    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.dateWeather)
        val dayOfWeek = itemView.findViewById<TextView>(R.id.dayOfWeek)
        val imgIcon = itemView.findViewById<ImageView>(R.id.iconWeatherForecast)
        val maxTemp = itemView.findViewById<TextView>(R.id.maxTemp)
        val minTemp = itemView.findViewById<TextView>(R.id.minTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item, parent, false)
        return ForecastViewHolder(itemView)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {


        val list = listForecast[position]
        val date = list.data
        val maxValue = list.maxValue
        val minValue = list.minValue
        val icon = list.icon

        val imgCon = "$URL_ICON$icon.png"
        val formatTempMax: String = DecimalFormat("#0").format(maxValue)
        val formatTempMin: String = DecimalFormat("#0").format(minValue)

        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val dateTxt: Date = simpleDateFormat.parse(date) as Date
        simpleDateFormat = SimpleDateFormat("d MMMM")
        val formatDate = simpleDateFormat.format(dateTxt)
        calendar.time = dateTxt

        holder.date.text = formatDate

        when (calendar[Calendar.DAY_OF_WEEK]) {
            Calendar.MONDAY -> holder.dayOfWeek.text = "Понедельник"
            Calendar.TUESDAY -> holder.dayOfWeek.text = "Вторник"
            Calendar.WEDNESDAY -> holder.dayOfWeek.text = "Среда"
            Calendar.THURSDAY -> holder.dayOfWeek.text = "Четверг"
            Calendar.FRIDAY -> holder.dayOfWeek.text = "Пятница"
            Calendar.SATURDAY -> holder.dayOfWeek.text = "Суббота"
            Calendar.SUNDAY -> holder.dayOfWeek.text = "Воскресенье"
        }

        holder.maxTemp.text = formatTempMax
        holder.minTemp.text = formatTempMin

        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(imgCon)
            .into(holder.imgIcon)

    }

    override fun getItemCount(): Int = listForecast.size

    fun addListWeather(list: ArrayList<ReadyModelWeather>) {
        this.listForecast.apply {
            clear()
            addAll(list)
        }
    }

}