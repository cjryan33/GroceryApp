package com.example.groceryappprojectcharles.presenter.product_by_sub_id

import com.example.groceryappprojectcharles.model.remote.response.ProductsBySubIDResponse

interface ProductBySubIdMVP {
    interface ProductBySubIdPresenter {
        fun getProductsBySubId(subId: Int) : String
    }

    interface ProductBySubIdView {
        fun setResult(productsBySubIDResponse: ProductsBySubIDResponse)
        fun onLoad(isLoading: Boolean)
    }
}