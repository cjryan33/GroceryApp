package com.example.groceryappprojectcharles.presenter.search

import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.response.SearchResponse
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.SearchVolleyHandler

class SearchPresenter(
    private val searchVolleyHandler: SearchVolleyHandler,
    private val searchView: SearchMVP.SearchView
) : SearchMVP.SearchPresenter{
    override fun searchProduct(search: String): String {
        searchView.onSearchLoad(true)
        val message = searchVolleyHandler.searchProduct(search, object : OperationalCallback.Search {
            override fun onSearchSuccess(searchResponse: SearchResponse) {
                searchView.onSearchLoad(false)
                searchView.setSearchResult(searchResponse)
            }

            override fun onError(message: String) {
                searchView.onSearchLoad(false)
            }
        })
        return message.toString()
    }

}