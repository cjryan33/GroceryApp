package com.example.groceryappprojectcharles.model.remote.volleyhandlers

import android.content.Context
import android.content.Intent
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.response.LoginResponse
import com.example.groceryappprojectcharles.model.remote.response.OrderData
import com.example.groceryappprojectcharles.model.remote.response.OrderResponse
import com.example.groceryappprojectcharles.model.remote.response.Product
import com.example.groceryappprojectcharles.view.OrderConfirmedActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class OrderVolleyHandler(private val context: Context) {
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun addOrder(orderData: OrderData, callback: OperationalCallback.Order) {
        val url = Constants.BASE_URL + Constants.ORDER_END_POINT

        val shippingAddress = JSONObject()
        shippingAddress.put("city",orderData.shippingAddress.city)
        shippingAddress.put("houseNo",orderData.shippingAddress.houseNo)
        shippingAddress.put("pincode",orderData.shippingAddress.pincode)
        shippingAddress.put("streetName",orderData.shippingAddress.streetName)
        shippingAddress.put("type",orderData.shippingAddress.type)

        val orderSummary = JSONObject()
        orderSummary.put("deliveryCharges",orderData.orderSummary.deliveryCharges)
        orderSummary.put("discount", orderData.orderSummary.discount)
        orderSummary.put("ourPrice", orderData.orderSummary.ourPrice)
        orderSummary.put("totalAmount", orderData.orderSummary.totalAmount)

        val user = JSONObject()
        user.put("email",orderData.user.email)

        val productList = JSONArray()
        for (i in orderData.products.indices) {
            val temp = JSONObject()
            temp.put("price", orderData.products[i].price)
            temp.put("quantity", orderData.products[i].quantity)
            temp.put("productName", orderData.products[i].productName)
            productList.put(temp)
        }


        val sharedPreferences =
            context.getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE)

        val userData = JSONObject()
        userData.put("userId", sharedPreferences.getString(Constants.USER_ID, ""))
        userData.put("orderSummary", orderSummary)
        userData.put("products", productList)
        userData.put("shippingAddress", shippingAddress)
        userData.put("user", user)
        user.put("orderStatus", "completed")


        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            userData,
            {
            callback.onSuccess("ok")
            },
            { error: VolleyError ->
                error.printStackTrace()
            }
        )
        requestQueue.add(request)
    }

    fun getOrders(callback: OperationalCallback.GetOrder) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE)
        val url =
            Constants.BASE_URL + Constants.ORDER_END_POINT + "/" + sharedPreferences.getString(
                Constants.USER_ID,
                ""
            )
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                val gson = Gson()
                val orderResponse: OrderResponse = gson.fromJson(it,
                    OrderResponse::class.java)
                callback.onOrderSuccess(orderResponse)

            },
            { error: VolleyError ->
                error.printStackTrace()
            }
        )
        requestQueue.add(request)
    }
}