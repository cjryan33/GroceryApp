package com.example.groceryappprojectcharles.presenter.login

import com.example.groceryappprojectcharles.model.remote.User

interface LoginMVP {
    interface LoginPresenter {
        fun loginUser(user: User): String
    }

    interface LoginView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}