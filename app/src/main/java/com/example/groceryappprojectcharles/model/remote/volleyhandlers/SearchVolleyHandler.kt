package com.example.groceryappprojectcharles.model.remote.volleyhandlers

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.data.SearchData
import com.example.groceryappprojectcharles.model.remote.response.SearchResponse
import com.google.gson.Gson
import org.json.JSONObject

class SearchVolleyHandler( val context:Context) {
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private lateinit var searchList: MutableList<SearchData>

    fun searchProduct(search: String, callback: OperationalCallback.Search){
        val url = Constants.BASE_URL + Constants.SEARCH_END_POINT + search
        var message: String? = null

        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                searchList = mutableListOf()
                val gson = Gson()
                val searchResponse: SearchResponse =
                    gson.fromJson(it, SearchResponse::class.java)
                if (!searchResponse.error) {
                    callback.onSearchSuccess(searchResponse)
                } else
                    callback.onError("Failed")

            },
            {
                    error: VolleyError ->
                error.printStackTrace()
                callback.onError(message.toString())
            }
        )
        requestQueue.add(request)
    }
}