package com.example.groceryappprojectcharles.presenter.category

interface CategoryMVP {
    interface CategoryPresenter {
        fun categoryCall(): String
    }

    interface CategoryView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}