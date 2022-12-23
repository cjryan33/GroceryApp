package com.example.groceryappprojectcharles.model.remote.volleyhandlers

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappprojectcharles.model.local.entity.Address
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.Constants.USER_ID
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import org.json.JSONObject

class AddressVolleyHandler(val context: Context) {
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun addAddress(address: Address, callback: OperationalCallback.Address) {
        val url = Constants.BASE_URL + Constants.ADDRESS_END_POINT
        val userData = JSONObject()
        val sharedPreferences = context.getSharedPreferences(Constants.LOGIN_SHARED_PREF,Context.MODE_PRIVATE)

        userData.put("pincode", address.zip )
        userData.put("city", address.city)
        userData.put("streetName", address.street)
        userData.put("houseNo", address.houseNo)
        userData.put("type", address.type)
        userData.put("userId", sharedPreferences.getString(USER_ID,""))

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
    }
}