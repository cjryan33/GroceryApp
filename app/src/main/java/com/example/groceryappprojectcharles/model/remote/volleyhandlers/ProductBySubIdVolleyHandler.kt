package com.example.groceryappprojectcharles.model.remote.volleyhandlers

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.response.ProductsBySubIDResponse
import com.google.gson.Gson

class ProductBySubIdVolleyHandler(private val context: Context) {
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getProductsBySubId(subId: Int, callback: OperationalCallback.ProductsBySubId){
        val url = "${Constants.BASE_URL}${Constants.PRODUCTS_BY_SUB_ID_END_POINT}${subId}"
        val message: String? = null

        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                val gson = Gson()
                val productsBySubIDResponse: ProductsBySubIDResponse =
                    gson.fromJson(it, ProductsBySubIDResponse::class.java)
                if (!productsBySubIDResponse.error) {
                    callback.onSuccess(productsBySubIDResponse)

                } else
                    callback.onError("Failed")

            },
            {
                    error: VolleyError ->
                error.printStackTrace()
                callback.onError(message.toString())
            }
        )
        requestQueue.add(request)
    }
}