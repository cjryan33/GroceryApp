package com.example.groceryappprojectcharles.presenter.address

import com.example.groceryappprojectcharles.model.local.entity.Address

interface AddressMVP {
    interface AddressPresenter {
        fun addAddress(address:Address) : String
    }

    interface AddressView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}