package com.example.groceryappprojectcharles.presenter.registration

import com.example.groceryappprojectcharles.model.remote.Data

interface RegistrationMVP {
    interface RegistrationPresenter {
        fun registerUser(data: Data): String
    }

    interface RegistrationView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}