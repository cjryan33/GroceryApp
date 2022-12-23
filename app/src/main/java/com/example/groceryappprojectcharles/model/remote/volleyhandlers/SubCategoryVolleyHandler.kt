package com.example.groceryappprojectcharles.model.remote.volleyhandlers

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappprojectcharles.model.local.AppDatabase
import com.example.groceryappprojectcharles.model.local.dao.SubCategoryDao
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.Constants.SUBCATEGORY_END_POINT
import com.example.groceryappprojectcharles.model.remote.OperationalCallback
import com.example.groceryappprojectcharles.model.remote.response.SubCatResponse
import com.google.gson.Gson

class SubCategoryVolleyHandler(val context: Context) {
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private var subCategoryDao: SubCategoryDao? = null

    fun getSubCategories(catId: Int, callback: OperationalCallback.SubCategory){
        initDB()
        val url = "${Constants.BASE_URL}$SUBCATEGORY_END_POINT$catId"
        val message: String? = null

        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                val gson = Gson()
                val subCategoryResponse: SubCatResponse =
                    gson.fromJson(it, SubCatResponse::class.java)
                if (!subCategoryResponse.error) {
                    callback.onSubCatSuccess(subCategoryResponse)

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

    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(context.applicationContext)
        subCategoryDao = appDB?.getSubCategoryDao()
    }
}