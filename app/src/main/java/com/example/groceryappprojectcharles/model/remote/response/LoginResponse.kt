package com.example.groceryappprojectcharles.model.remote.response

data class LoginResponse(
    val token: String,
    val user: User
)