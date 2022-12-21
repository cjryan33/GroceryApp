package com.example.groceryappprojectcharles.model.remote.response

data class Product(
    val _id: String,
    val price: Int,
    val productName: String,
    val quantity: Int
)