package com.example.groceryappprojectcharles.model.remote

import com.example.groceryappprojectcharles.model.remote.response.SearchResponse

interface OperationalCallback {
    fun onSuccess(message:String)
    fun onError(message: String)

    interface Search{
        fun onSearchSuccess(searchResponse: SearchResponse)
        fun onError(message: String)
    }
}