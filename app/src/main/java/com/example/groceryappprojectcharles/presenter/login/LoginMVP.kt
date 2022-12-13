package com.example.groceryappprojectcharles.presenter.login

import com.example.groceryappprojectcharles.model.remote.data.LoginData

interface LoginMVP {
    interface LoginPresenter {
        fun loginUser(user: LoginData): String
    }

    interface LoginView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}