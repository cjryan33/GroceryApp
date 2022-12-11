package com.example.groceryappprojectcharles.presenter.login

import com.example.groceryappprojectcharles.model.remote.volleyhandlers.LoginVolleyHandler
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.User

class LoginPresenter(
    private val volleyHandler: LoginVolleyHandler,
    private val loginView: LoginMVP.LoginView
) : LoginMVP.LoginPresenter {
    override fun loginUser(user: User): String {
        loginView.onLoad(true)
        val message = volleyHandler.loginUser(user, object : OperationalCallback {
            override fun onSuccess(message: String) {
                loginView.onLoad(false)
                loginView.setResult(message)
            }

            override fun onError(message: String) {
                loginView.onLoad(false)
                loginView.setResult(message)
            }
        })
        return message
    }
}