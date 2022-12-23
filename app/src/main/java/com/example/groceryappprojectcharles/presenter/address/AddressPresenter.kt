package com.example.groceryappprojectcharles.presenter.address

import com.example.groceryappprojectcharles.model.local.entity.Address
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.AddressVolleyHandler

class AddressPresenter(
    private val volleyHandler: AddressVolleyHandler,
    private val addressView: AddressMVP.AddressView
) : AddressMVP.AddressPresenter {

    override fun addAddress(address: Address) : String {
        addressView.onLoad(true)
        val message = volleyHandler.addAddress(address, object : OperationalCallback.Address {
            override fun onSuccess(message: String) {
                addressView.onLoad(false)
                addressView.setResult(message)
            }

            override fun onError(message: String) {
                addressView.onLoad(false)
                addressView.setResult(message)
            }

        })
        return message.toString()
    }
}