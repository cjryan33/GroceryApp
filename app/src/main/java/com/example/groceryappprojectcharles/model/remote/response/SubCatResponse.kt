package com.example.groceryappprojectcharles.model.remote.data

data class SubCatResponse(
    val count: Int,
    val `data`: List<Data>,
    val error: Boolean
)