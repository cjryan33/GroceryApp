package com.example.groceryappprojectcharles.model.remote.volleyhandlers

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappprojectcharles.model.local.AppDatabase
import com.example.groceryappprojectcharles.model.local.dao.CategoryDao
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.Constants.CATEGORY_END_POINT
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.response.CategoryResponse
import com.google.gson.Gson

class CategoryVolleyHandler(private val context: Context) {
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private var categoryDao: CategoryDao? = null

    fun categoryCall(callback: OperationalCallback.Category): String {
        initDB()
        val url = Constants.BASE_URL + CATEGORY_END_POINT
        val message: String? = null

        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                val gson = Gson()
                val categoryResponse: CategoryResponse =
                    gson.fromJson(it, CategoryResponse::class.java)
                callback.onSuccess(categoryResponse)
            },
            {
                //do nothing
            }
        )
        requestQueue.add(request)
        return message.toString()
    }

    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(context.applicationContext)
        categoryDao = appDB?.getCategoryDao()
    }
}