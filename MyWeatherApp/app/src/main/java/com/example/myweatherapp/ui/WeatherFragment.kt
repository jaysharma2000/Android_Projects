package com.example.myweatherapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myweatherapp.R
import com.example.myweatherapp.api.RetrofitClient
import com.example.myweatherapp.model.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {

    lateinit var tv_weatherTemp: TextView
    lateinit var tv_writecity: TextView
    lateinit var btnGetWeather:Button
    private var currentWeatherData: WeatherData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        btnGetWeather = view.findViewById(R.id.btnGetWeather)
        val et_enterCity = view.findViewById<EditText>(R.id.et_enterCity)
        tv_writecity = view.findViewById(R.id.tv_writecity)
        tv_weatherTemp = view.findViewById(R.id.tv_weatherTemp)

        // Fix: Correctly pass a lambda to the OnClickListener
        btnGetWeather.setOnClickListener {
            val city = et_enterCity.text.toString().trim()
            if (city.isNotEmpty()) {
                getWeatherUpdates(city)
            } else {
                Toast.makeText(requireContext(), "Please enter a city", Toast.LENGTH_SHORT).show()
            }
        }

        tv_writecity.setOnClickListener {
            currentWeatherData?.let { weatherData ->
                val action = WeatherFragmentDirections
                    .actionWeatherFragmentToWeatherDetailsFragment(weatherData)
                findNavController().navigate(action)
            } ?: run {
                Toast.makeText(requireContext(), "No weather data available", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    // Correct parameter type to `String` and add proper response handling
    fun getWeatherUpdates(city: String) {
        val apiKey = "c45449224162fc5a08e6cf2278b96dd3"

        RetrofitClient.instance.getWeather(city, apiKey).enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.isSuccessful) {
                    response.body()?.let { weatherData ->
                        // Update TextViews with weather data
                        currentWeatherData = weatherData
                        tv_weatherTemp.text = "${weatherData.main.temp - 273.15}°C"
                        tv_writecity.text = weatherData.name

//                        val action = WeatherFragmentDirections
//                            .actionWeatherFragmentToWeatherDetailsFragment(weatherData)
//                        findNavController().navigate(action)
                    }
                } else {
                    Toast.makeText(requireContext(), "City not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.d("OnClick Error!!!!!!!!!!!!", "${t.message}")
                Toast.makeText(requireContext(), "Failed to get data: ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}
