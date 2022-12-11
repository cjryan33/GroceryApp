package com.example.groceryappprojectcharles.presenter.category

import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.CategoryVolleyHandler

class CategoryPresenter(
    private val volleyHandler: CategoryVolleyHandler,
    private val categoryView: CategoryMVP.CategoryView
) : CategoryMVP.CategoryPresenter {
    override fun categoryCall(): String {
        categoryView.onLoad(true)
        val message = volleyHandler.categoryCall(object : OperationalCallback {
            override fun onSuccess(message: String) {
                categoryView.onLoad(false)
                categoryView.setResult(message)
            }

            override fun onError(message: String) {
                categoryView.onLoad(false)
                categoryView.setResult(message)
            }
        })
        return message
    }
}