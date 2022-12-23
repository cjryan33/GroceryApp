package com.example.groceryappprojectcharles.presenter.order

import com.example.groceryappprojectcharles.model.remote.response.OrderData
import com.example.groceryappprojectcharles.model.remote.response.OrderResponse
import org.json.JSONObject

interface OrderMVP {
    interface OrderPresenter {
        fun addOrder(orderData: OrderData)
        fun getOrders()
    }

    interface OrderView {
        fun setOrderResult(message:String)
        fun setOrderResult(orderResponse: OrderResponse)
    }
}