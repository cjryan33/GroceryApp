package com.example.groceryappprojectcharles.model.remote.response

data class OrderSummary(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)