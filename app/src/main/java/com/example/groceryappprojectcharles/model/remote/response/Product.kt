package com.example.groceryappprojectcharles.model.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val _id: String,
    val price: Float,
    val productName: String,
    val quantity: Int
) : Parcelable