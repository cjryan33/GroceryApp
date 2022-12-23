package com.example.groceryappprojectcharles.presenter.login

import com.example.groceryappprojectcharles.model.remote.volleyhandlers.LoginVolleyHandler
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.data.LoginData
import com.example.groceryappprojectcharles.model.remote.data.User
import com.example.groceryappprojectcharles.model.remote.response.LoginResponse

class LoginPresenter(
    private val volleyHandler: LoginVolleyHandler,
    private val loginView: LoginMVP.LoginView
) : LoginMVP.LoginPresenter {
    override fun loginUser(user: LoginData): String {
        loginView.onLoad(true)
        val message = volleyHandler.loginUser(user, object : OperationalCallback {
            override fun onSuccess(message: String, loginResponse: LoginResponse) {
                loginView.onLoad(false)
                loginView.setResult(message, loginResponse)
            }

            override fun onError(message: String) {
                loginView.onLoad(false)
                loginView.setResult(message,LoginResponse("x", User(1,"","","","","","")))
            }
        })
        return message
    }
}