package com.example.groceryappprojectcharles.model.remote.volleyhandlers

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappprojectcharles.model.local.entity.Address
import com.example.groceryappprojectcharles.model.local.entity.Order
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import org.json.JSONObject

class OderVolleyHandler(private val context: Context) {
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun addOrder(order: Order, callback: OperationalCallback.Address) : String {
        val url = Constants.BASE_URL + Constants.ADDRESS_END_POINT
        val userData = JSONObject()
        var message: String? = null
        val sharedPreferences = context.getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE)
        userData.put("userId", sharedPreferences.getString(Constants.USER_ID,""))

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            userData,
            {
                    response: JSONObject ->
                callback.onSuccess(response.getString("message"))
            },
            {
                    error: VolleyError ->
                error.printStackTrace()
                callback.onError(error.toString())
            }
        )
        requestQueue.add(request)
        return message.toString()
    }
}