package com.example.groceryappprojectcharles.model.remote.response

import com.example.groceryappprojectcharles.model.remote.data.SearchData

data class SearchResponse(
    val count: Int,
    val data: List<SearchData>,
    val error: Boolean
)