package com.example.groceryappprojectcharles.model.remote.response

data class OrderResponse(
    val count: Int,
    val `data`: List<Data>,
    val error: Boolean
)