package com.example.groceryappprojectcharles.model.remote.volleyhandlers

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.data.LoginData
import com.example.groceryappprojectcharles.model.remote.response.LoginResponse
import com.google.gson.Gson
import org.json.JSONObject

class LoginVolleyHandler(private val context: Context) {
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun loginUser(user: LoginData, callback: OperationalCallback) : String {
        val url = Constants.BASE_URL + Constants.LOGIN_END_POINT
        val userData = JSONObject()
        var message: String? = null

        userData.put(Constants.EMAIL, user.email)
        userData.put(Constants.PASSWORD, user.password)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            userData,
            {
                    response: JSONObject ->
                message = "success"
                val gson = Gson()
                val loginResponse : LoginResponse = gson.fromJson(response.toString(),LoginResponse::class.java)
                callback.onSuccess(message.toString(), loginResponse)
            },
            {
                    error: VolleyError ->
                if (error.networkResponse.statusCode == 404){
                    message = "Login credentials invalid. Please try again"
                } else if (error.networkResponse.statusCode == 500) {
                    message = "Illegal arguments: undefined, string"
                }
                callback.onError(message.toString())
            }
        )
        requestQueue.add(request)
        return message.toString()
    }
}