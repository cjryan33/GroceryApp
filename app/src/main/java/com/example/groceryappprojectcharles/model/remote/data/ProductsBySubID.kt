package com.example.groceryappprojectcharles.model.remote.data

data class ProductsBySubID(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val created: String,
    val description: String,
    val image: String,
    val mrp: Float,
    val position: Int,
    val price: Float,
    val productName: String,
    val quantity: Int,
    val status: Boolean,
    val subId: Int,
    val unit: String
)