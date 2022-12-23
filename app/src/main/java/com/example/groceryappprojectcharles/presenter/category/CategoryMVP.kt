package com.example.groceryappprojectcharles.presenter.category

import com.example.groceryappprojectcharles.model.remote.response.CategoryResponse

interface CategoryMVP {
    interface CategoryPresenter {
        fun categoryCall(): String
    }

    interface CategoryView {
        fun setResult(categoryResponse: CategoryResponse)
        fun onLoad(isLoading: Boolean)
    }
}