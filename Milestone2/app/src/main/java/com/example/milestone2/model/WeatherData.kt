package com.example.milestone2.model

import android.health.connect.datatypes.units.Pressure
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherData (
    val id: Int,
    val main:Main,
    val wind:Wind,
    val name: String
): Parcelable

@Parcelize
data class Main(
    val temp:Double,
    val pressure: Int,
    val humidity:Int,
    val sea_level:Int
):Parcelable

@Parcelize
data class Wind(
    val speed:Double
): Parcelable