package com.example.groceryappprojectcharles.model.remote.response

import com.example.groceryappprojectcharles.model.remote.data.User

data class LoginResponse(
    val token: String,
    val user: User
)