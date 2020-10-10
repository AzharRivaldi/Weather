package com.azhar.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.azhar.weatherapp.R
import com.azhar.weatherapp.model.ModelNextDay
import kotlinx.android.synthetic.main.list_item_next_days.view.*
import java.util.*

/**
 * Created by Azhar Rivaldi on 07-10-2020
 */

class NextDayAdapter(private val mContext: Context,
                     private val items: List<ModelNextDay>) : RecyclerView.Adapter<NextDayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_next_days, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]

        holder.tvNameDay.text = data.nameDay
        holder.tvDate.text = data.nameDate
        holder.tvMinTemp.text = String.format(Locale.getDefault(), "%.0f°C", data.tempMin)
        holder.tvMaxTemp.text = String.format(Locale.getDefault(), "%.0f°C", data.tempMax)

        if (data.descWeather == "broken clouds") {
            holder.iconTemp.setAnimation(R.raw.broken_clouds)
        } else if (data.descWeather == "light rain") {
            holder.iconTemp.setAnimation(R.raw.light_rain)
        } else if (data.descWeather == "overcast clouds") {
            holder.iconTemp.setAnimation(R.raw.overcast_clouds)
        } else if (data.descWeather == "moderate rain") {
            holder.iconTemp.setAnimation(R.raw.moderate_rain)
        } else if (data.descWeather == "few clouds") {
            holder.iconTemp.setAnimation(R.raw.few_clouds)
        } else if (data.descWeather == "heavy intensity rain") {
            holder.iconTemp.setAnimation(R.raw.heavy_intentsity)
        } else if (data.descWeather == "clear sky") {
            holder.iconTemp.setAnimation(R.raw.clear_sky)
        } else if (data.descWeather == "scattered clouds") {
            holder.iconTemp.setAnimation(R.raw.scattered_clouds)
        } else {
            holder.iconTemp.setAnimation(R.raw.unknown)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cvListNextDays: CardView
        var tvNameDay: TextView
        var tvDate: TextView
        var tvMaxTemp: TextView
        var tvMinTemp: TextView
        var iconTemp: LottieAnimationView

        init {
            cvListNextDays = itemView.cvListNextDays
            tvNameDay = itemView.tvNameDay
            tvDate = itemView.tvDate
            tvMaxTemp = itemView.tvMaxTemp
            tvMinTemp = itemView.tvMinTemp
            iconTemp = itemView.iconTemp
        }
    }
}