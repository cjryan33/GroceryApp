package com.example.groceryappprojectcharles.presenter.category

import com.example.groceryappprojectcharles.model.remote.User

interface CategoryMVP {
    interface CategoryPresenter {
        fun categoryCall(): String
    }

    interface CategoryView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}