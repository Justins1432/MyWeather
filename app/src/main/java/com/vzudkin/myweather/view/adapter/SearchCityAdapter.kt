package com.vzudkin.myweather.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.vzudkin.myweather.R
import com.vzudkin.myweather.model.data.DirectGeocoding
import java.util.*
import kotlin.collections.ArrayList

interface ClickCity {
    fun onItemClickCity(directGeocoding: DirectGeocoding)
}

class SearchCityAdapter(val clickCity: ClickCity) :
    RecyclerView.Adapter<SearchCityAdapter.SearchCityViewHolder>(),
    Filterable {

    private val citiesList = ArrayList<DirectGeocoding>()
    private var filterCitiesList = ArrayList<DirectGeocoding>()

    init {
        filterCitiesList = citiesList
    }

    inner class SearchCityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val citySearch = itemView.findViewById<TextView>(R.id.citySearch)
        val countrySearch = itemView.findViewById<TextView>(R.id.countrySearch)
        val regionSearch = itemView.findViewById<TextView>(R.id.regionSearch)
        val cardViewCity = itemView.findViewById<CardView>(R.id.cityForeground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCityViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_city_item, parent, false)
        return SearchCityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchCityViewHolder, position: Int) {
        val listCities: DirectGeocoding = filterCitiesList[position]
        holder.citySearch.text = listCities.name
        holder.countrySearch.text = listCities.country
        holder.regionSearch.text = listCities.state

        holder.cardViewCity.setOnClickListener {
            clickCity.onItemClickCity(listCities)
        }
    }

    override fun getItemCount(): Int = filterCitiesList.size

    fun searchCity(list: ArrayList<DirectGeocoding>) {
        citiesList.apply {
            clear()
            addAll(list)
        }
    }

    fun clearList() {
        val size = filterCitiesList.size
        filterCitiesList.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val citySearch = constraint.toString()
                if (citySearch.isEmpty()) {
                    filterCitiesList = citiesList
                } else {
                    val resultCitiesList = ArrayList<DirectGeocoding>()
                    for (city in citiesList) {
                        if (city.name.lowercase(Locale.ROOT).contains(citySearch.lowercase())) {
                            resultCitiesList.add(city)
                        }
                    }
                    filterCitiesList = resultCitiesList
                }
                val filterResults = FilterResults()
                filterResults.values = filterCitiesList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filterCitiesList = p1?.values as ArrayList<DirectGeocoding>
                notifyDataSetChanged()
            }
        }
    }

}


