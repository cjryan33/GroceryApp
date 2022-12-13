package com.example.groceryappprojectcharles.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.databinding.ActivityGroceryDashboardBinding
import com.example.groceryappprojectcharles.model.local.AppDatabase
import com.example.groceryappprojectcharles.model.local.dao.CategoryDao
import com.example.groceryappprojectcharles.model.remote.Constants.LOGIN_SHARED_PREF
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.CategoryVolleyHandler
import com.example.groceryappprojectcharles.presenter.category.CategoryMVP
import com.example.groceryappprojectcharles.presenter.category.CategoryPresenter
import com.example.groceryappprojectcharles.model.remote.adapters.CategoryAdapter
import com.example.groceryappprojectcharles.model.remote.adapters.SearchAdapter
import com.example.groceryappprojectcharles.model.remote.data.SearchData
import com.example.groceryappprojectcharles.model.remote.response.SearchResponse
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.SearchVolleyHandler
import com.example.groceryappprojectcharles.presenter.search.SearchMVP
import com.example.groceryappprojectcharles.presenter.search.SearchPresenter

class GroceryDashboardActivity : AppCompatActivity(), CategoryMVP.CategoryView, SearchMVP.SearchView  {
    private lateinit var binding: ActivityGroceryDashboardBinding
    private lateinit var catPresenter: CategoryPresenter
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var catAdapter: CategoryAdapter
    private lateinit var searchAdapter: SearchAdapter

    private var categoryDao: CategoryDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGroceryDashboardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initDB()
        setupNavDrawer()
        setupCategories()
        setupEvents()
    }

    private fun setupEvents() {
        searchPresenter = SearchPresenter(SearchVolleyHandler(this@GroceryDashboardActivity),this )
        binding.apply {
            btnSearch.setOnClickListener {
               searchPresenter.searchProduct(edtSearch.text.toString().trim())
            }
        }
    }

    private fun setupCategories() {
        catPresenter = CategoryPresenter(CategoryVolleyHandler(this), this)
        catPresenter.categoryCall()
        val categoryList = categoryDao?.getAllCategories()
        catAdapter = CategoryAdapter(this,categoryList!!)
        binding.rvCategories.layoutManager = GridLayoutManager(this,2)
        binding.rvCategories.adapter = catAdapter

    }

    private fun setupNavDrawer() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
            setDisplayShowTitleEnabled(false)
        }

        var logoutDialog = setupLogoutDialog()

        binding.navView.setNavigationItemSelectedListener { menuItems ->
            when (menuItems.itemId) {
                R.id.navLogout -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    logoutDialog.show()
                }
            }
            true
        }
    }

    private fun setupLogoutDialog(): AlertDialog.Builder {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Logout?")
        alertDialog.setPositiveButton("Confirm") { _, _ ->
            val sharedPref =
                application.getSharedPreferences(LOGIN_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            editor.commit()
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setNegativeButton("Cancel") { _, _ ->
            //do nothing
        }
        return alertDialog
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(applicationContext)
        categoryDao = appDB?.getCategoryDao()
    }

    override fun setResult(message: String) {

    }

    override fun setSearchResult(searchResponse: SearchResponse) {
        val searchList = mutableListOf<SearchData>()
        for (i in searchResponse.data.indices) {
            searchList.add(searchResponse.data[i])
        }
        searchAdapter = SearchAdapter(this, searchList)
        binding.apply {
            rvCategories.visibility = View.GONE
            svDeals.visibility=View.GONE
            rvSearch.visibility = View.VISIBLE
            rvSearch.layoutManager = LinearLayoutManager(this@GroceryDashboardActivity)
            rvSearch.adapter = searchAdapter
        }
    }

    override fun onLoad(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else
            binding.progressBar.visibility = View.GONE
    }
}