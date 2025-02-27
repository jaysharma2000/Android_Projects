package com.example.milestone4.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: Int,
    val name: String,
    val amount: Double,
    val category: String,
   ): Parcelable
