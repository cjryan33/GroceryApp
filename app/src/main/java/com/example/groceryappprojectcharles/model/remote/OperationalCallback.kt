package com.example.groceryappprojectcharles.model.remote

interface OperationalCallback {
    fun onSuccess(message:String)
    fun onError(message: String)
}