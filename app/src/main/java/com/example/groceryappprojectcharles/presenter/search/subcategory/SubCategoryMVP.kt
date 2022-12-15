package com.example.groceryappprojectcharles.presenter.subcategory

import com.example.groceryappprojectcharles.model.remote.response.SearchResponse
import com.example.groceryappprojectcharles.model.remote.response.SubCatResponse

interface SubCategoryMVP {
    interface SubCategoryPresenter {
        fun getSubCategories(catId: Int): String
    }

    interface SubCategoryView {
        fun setSubCategoryResult(subCatResponse: SubCatResponse)
        fun onSubCategoryLoad(isLoading: Boolean)
    }
}