package com.example.groceryappprojectcharles.presenter.subcategory

import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.response.SubCatResponse
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.SubCategoryVolleyHandler


class SubCategoryPresenter(
    private val subCategoryVolleyHandler: SubCategoryVolleyHandler,
    private val subCategoryView: SubCategoryMVP.SubCategoryView
) :SubCategoryMVP.SubCategoryPresenter {
    override fun getSubCategories(catId: Int): String {
        subCategoryView.onSubCategoryLoad(true)
        val message = subCategoryVolleyHandler.getSubCategories(catId, object : OperationalCallback.SubCategory{
            override fun onSubCatSuccess(subCatResponse: SubCatResponse) {
                subCategoryView.onSubCategoryLoad(false)
                subCategoryView.setSubCategoryResult(subCatResponse)
            }

            override fun onError(message: String) {
                subCategoryView.onSubCategoryLoad(false)
            }

        })
        return message.toString()
    }
}