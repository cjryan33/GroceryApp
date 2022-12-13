package com.example.groceryappprojectcharles.model.remote.volleyhandlers

import android.content.Context
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappprojectcharles.model.remote.Constants.BASE_URL
import com.example.groceryappprojectcharles.model.remote.Constants.EMAIL
import com.example.groceryappprojectcharles.model.remote.Constants.FIRST_NAME
import com.example.groceryappprojectcharles.model.remote.Constants.MESSAGE_RESPONSE
import com.example.groceryappprojectcharles.model.remote.Constants.PASSWORD
import com.example.groceryappprojectcharles.model.remote.Constants.PHONE_NUMBER
import com.example.groceryappprojectcharles.model.remote.Constants.REGISTRATION_END_POINT
import com.example.groceryappprojectcharles.model.remote.data.RegisterData
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import org.json.JSONObject

class RegistrationVolleyHandler(private val context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun registerUser(data: RegisterData, callback: OperationalCallback) : String {
        val url = BASE_URL + REGISTRATION_END_POINT
        val userData = JSONObject()
        var message: String? = null

        userData.put(FIRST_NAME, data.firstName )
        userData.put(EMAIL, data.email)
        userData.put(PHONE_NUMBER,data.mobile)
        userData.put(PASSWORD, data.password)

        val request = JsonObjectRequest(
            Method.POST,
            url,
            userData,
            {
            response: JSONObject ->
                message = response.getString(MESSAGE_RESPONSE)
                callback.onSuccess(message.toString())
            },
            {
                error: VolleyError ->
                error.printStackTrace()
                if (error.networkResponse.statusCode == 400) {
                    message = "email already registered"
                }
                callback.onError(message.toString())
            }
        )
        requestQueue.add(request)
        return message.toString()
    }
}