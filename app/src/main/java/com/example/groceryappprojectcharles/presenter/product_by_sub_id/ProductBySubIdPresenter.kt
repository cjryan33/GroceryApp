package com.example.groceryappprojectcharles.presenter.product_by_sub_id

import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.response.ProductsBySubIDResponse
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.ProductBySubIdVolleyHandler

class ProductBySubIdPresenter(private val volleyHandler: ProductBySubIdVolleyHandler,
private val productBySubIdView: ProductBySubIdMVP.ProductBySubIdView) : ProductBySubIdMVP.ProductBySubIdPresenter {
    override fun getProductsBySubId(subId: Int): String {
        productBySubIdView.onLoad(true)
        val message = volleyHandler.getProductsBySubId(subId, object : OperationalCallback.ProductsBySubId{
            override fun onSuccess(productsBySubIDResponse: ProductsBySubIDResponse) {
                productBySubIdView.onLoad(false)
                productBySubIdView.setResult(productsBySubIDResponse)
            }

            override fun onError(message: String) {
                productBySubIdView.onLoad(false)
            }

        })
        return message.toString()
    }
}