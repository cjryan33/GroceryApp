package com.example.groceryappprojectcharles.presenter.search

import com.example.groceryappprojectcharles.model.remote.data.RegisterData
import com.example.groceryappprojectcharles.model.remote.response.SearchResponse

interface SearchMVP {
    interface SearchPresenter {
        fun searchProduct(search: String): String
    }

    interface SearchView {
        fun setSearchResult(searchResponse: SearchResponse)
        fun onSearchLoad(isLoading: Boolean)
    }
}