package com.example.groceryappprojectcharles.model.remote

import com.example.groceryappprojectcharles.model.remote.response.*
import org.json.JSONObject

interface OperationalCallback {
    fun onSuccess(message:String, loginResponse: LoginResponse)
    fun onError(message: String)

    interface Category{
        fun onSuccess(categoryResponse: CategoryResponse)
    }

    interface Address{
        fun onSuccess(message:String)
        fun onError(message: String)
    }
    interface Order{
        fun onSuccess(message: String)
    }
    interface GetOrder{
        fun onOrderSuccess(orderResponse: OrderResponse)
    }

    interface Search{
        fun onSearchSuccess(searchResponse: SearchResponse)
        fun onError(message: String)
    }
    interface SubCategory{
        fun onSubCatSuccess(subCatResponse: SubCatResponse)
        fun onError(message: String)
    }
    interface ProductsBySubId{
        fun onSuccess(productsBySubIDResponse: ProductsBySubIDResponse)
        fun onError(message: String)
    }
}