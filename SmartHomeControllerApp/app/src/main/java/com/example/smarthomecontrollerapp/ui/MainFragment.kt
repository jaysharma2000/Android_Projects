package com.example.smarthomecontrollerapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.smarthomecontrollerapp.R


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val button_settings = view.findViewById<Button>(R.id.button_settings)
        val button_dashboard = view.findViewById<Button>(R.id.button_dashboard)
        val seekBarFan = view.findViewById<SeekBar>(R.id.seekBarFan)
        val tvFanSpeed = view.findViewById<TextView>(R.id.tvFanSpeed)
        val seekBarThermostat = view.findViewById<SeekBar>(R.id.seekBarThermostat)
        val tvThermostatValue = view.findViewById<TextView>(R.id.tvThermostatValue)


        button_settings.setOnClickListener {
             findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }

        button_dashboard.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_dashBoardFragment)
        }

        seekBarFan.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update the TextView with the current progress
                tvFanSpeed.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        seekBarThermostat.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update the TextView with the current progress
                tvThermostatValue.text = "${progress.toString()}°C"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        return view
    }
}