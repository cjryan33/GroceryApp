package com.example.groceryappprojectcharles.model.remote.data

data class ProductsBySubIDResponse(
    val count: Int,
    val `data`: List<Data>,
    val error: Boolean
)