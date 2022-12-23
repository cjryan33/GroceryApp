package com.example.groceryappprojectcharles.model.remote.response

import com.example.groceryappprojectcharles.model.local.entity.SubCategory

data class SubCatResponse(
    val count: Int,
    val data: List<SubCategory>,
    val error: Boolean
)