package com.example.groceryappprojectcharles.presenter.login

import com.example.groceryappprojectcharles.model.remote.data.LoginData
import com.example.groceryappprojectcharles.model.remote.response.LoginResponse

interface LoginMVP {
    interface LoginPresenter {
        fun loginUser(user: LoginData): String
    }

    interface LoginView {
        fun setResult(message: String, loginResponse: LoginResponse)
        fun onLoad(isLoading: Boolean)
    }
}