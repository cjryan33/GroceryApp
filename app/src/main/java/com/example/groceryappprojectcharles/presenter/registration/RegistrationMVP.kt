package com.example.groceryappprojectcharles.presenter.registration

import com.example.groceryappprojectcharles.model.remote.data.RegisterData

interface RegistrationMVP {
    interface RegistrationPresenter {
        fun registerUser(data: RegisterData): String
    }

    interface RegistrationView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}