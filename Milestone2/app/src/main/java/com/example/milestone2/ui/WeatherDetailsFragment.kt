package com.example.milestone2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.milestone2.R
import com.example.milestone2.model.WeatherData

class WeatherDetailsFragment : Fragment() {

    lateinit var tv_pressure : TextView
    lateinit var tv_humidity : TextView
    lateinit var tv_sealevel :TextView
    lateinit var tv_speed: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather_details, container, false)

        tv_pressure = view.findViewById(R.id.tv_pressure)
        tv_humidity = view.findViewById(R.id.tv_humidity)
        tv_sealevel = view.findViewById(R.id.tv_sealevel)
        tv_speed = view.findViewById(R.id.tv_speed)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherdetails  = arguments?.getParcelable<WeatherData>("weatherData")

        if(weatherdetails != null){
            tv_pressure.text = "Pressure: ${weatherdetails.main.pressure.toString()}"
            tv_humidity.text = "Humidity: ${weatherdetails.main.humidity.toString()}"
            tv_sealevel.text = "Sealevel: ${weatherdetails.main.sea_level.toString()}"
            tv_speed.text = "Wind Speed: ${weatherdetails.wind.speed.toString()}"
        }else{
            Log.d("WeatherdetailsFragment", "Weatherdata is null")
        }
    }
}
