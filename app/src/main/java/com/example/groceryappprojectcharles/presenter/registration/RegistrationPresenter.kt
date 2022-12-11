package com.example.groceryappprojectcharles.presenter.registration

import com.example.groceryappprojectcharles.model.remote.Data
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.RegistrationVolleyHandler

class RegistrationPresenter(
    private val volleyHandler: RegistrationVolleyHandler,
    private val registrationView: RegistrationMVP.RegistrationView
) : RegistrationMVP.RegistrationPresenter {

    override fun registerUser(data: Data): String {
        registrationView.onLoad(true)
        val message = volleyHandler.registerUser(data, object : OperationalCallback {
            override fun onSuccess(message: String) {
                registrationView.onLoad(false)
                registrationView.setResult(message)
            }

            override fun onError(message: String) {
                registrationView.onLoad(false)
                registrationView.setResult(message)
            }
        })
        return message
    }
}