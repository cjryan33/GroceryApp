package com.example.groceryappprojectcharles.presenter.category

import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.response.CategoryResponse
import com.example.groceryappprojectcharles.model.remote.response.LoginResponse
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.CategoryVolleyHandler

class CategoryPresenter(
    private val volleyHandler: CategoryVolleyHandler,
    private val categoryView: CategoryMVP.CategoryView
) : CategoryMVP.CategoryPresenter {
    override fun categoryCall(): String {
        categoryView.onLoad(false)
        val message = volleyHandler.categoryCall(object : OperationalCallback.Category {
            override fun onSuccess(categoryResponse: CategoryResponse) {
                categoryView.setResult(categoryResponse)
            }
        })
        return message
    }
}