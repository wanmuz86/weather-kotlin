package com.muzaffar.weatherlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class CustomAdapter : Adapter<CustomAdapter.CustomViewHolder>() {
    var weatherList = LinkedList<JSONObject>()
     lateinit var context: Context;



    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateTextView:TextView = itemView.findViewById(R.id.dateTextView)
        var weatherTextView:TextView = itemView.findViewById(R.id.weatherTextView)
        var tempTextView:TextView = itemView.findViewById(R.id.tempTextView)
        var humidityTextView:TextView = itemView.findViewById(R.id.humidityTextView)
        var pressureTextView:TextView = itemView.findViewById(R.id.pressureTextView)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val context = parent.context
        var inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_row, parent, false)
        this.context = parent.context
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var currentWeather = weatherList[position]
        var date = Date(currentWeather.getLong("dt")* 1000)
        var formatter = SimpleDateFormat("dd-MM-yyyy")
        holder.dateTextView.text = "${formatter.format(date)}"
        holder.weatherTextView.text = currentWeather.getJSONArray("weather").getJSONObject(0).getString("main")
        holder.tempTextView.text = "%.2f".format(currentWeather.getJSONObject("temp").getDouble("day") - 273.15) + " C"
        holder.humidityTextView.text = "Humidity ${currentWeather.getString("humidity")}"
        holder.pressureTextView.text = "Pressure ${currentWeather.getString("pressure")}"
        var iconcode = currentWeather.getJSONArray("weather").getJSONObject(0).getString("icon")
        var iconurl = "https://openweathermap.org/img/w/" + iconcode + ".png";
        Glide.with(context).load(iconurl).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}