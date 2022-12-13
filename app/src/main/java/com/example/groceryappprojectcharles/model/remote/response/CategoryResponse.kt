package com.example.groceryappprojectcharles.model.remote.response

import com.example.groceryappprojectcharles.model.local.entity.Category

data class CategoryResponse(
    val count: Int,
    val `data`: List<Category>,
    val error: Boolean
)