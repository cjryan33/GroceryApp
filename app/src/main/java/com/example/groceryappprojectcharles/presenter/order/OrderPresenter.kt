package com.example.groceryappprojectcharles.presenter.order

import android.os.Message
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.response.OrderData
import com.example.groceryappprojectcharles.model.remote.response.OrderResponse
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.OrderVolleyHandler
import org.json.JSONObject

class OrderPresenter(
    private val volleyHandler: OrderVolleyHandler,
    private val orderView: OrderMVP.OrderView
) : OrderMVP.OrderPresenter{
    override fun addOrder(orderData: OrderData){
        volleyHandler.addOrder(orderData, object : OperationalCallback.Order {
            override fun onSuccess(message:String) {
                orderView.setOrderResult(message)
            }
        })
    }

    override fun getOrders() {
        volleyHandler.getOrders(object : OperationalCallback.GetOrder {
            override fun onOrderSuccess(orderResponse: OrderResponse) {
                orderView.setOrderResult(orderResponse)
            }
        })
    }

}