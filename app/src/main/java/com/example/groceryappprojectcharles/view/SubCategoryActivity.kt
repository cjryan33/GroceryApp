package com.example.groceryappprojectcharles.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.databinding.ActivitySubCategoryBinding
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.adapters.SubCatBeefFragmentAdapter
import com.example.groceryappprojectcharles.model.remote.adapters.SubCatChickenFragmentAdapter
import com.example.groceryappprojectcharles.model.remote.adapters.SubCatFruitsFragmentAdapter
import com.example.groceryappprojectcharles.model.remote.adapters.SubCatVegFragmentAdapter
import com.example.groceryappprojectcharles.model.remote.response.SubCatResponse
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.SubCategoryVolleyHandler
import com.example.groceryappprojectcharles.presenter.search.subcategory.SubCategoryMVP
import com.example.groceryappprojectcharles.presenter.search.subcategory.SubCategoryPresenter

class SubCategoryActivity : AppCompatActivity(), SubCategoryMVP.SubCategoryView {
    private lateinit var binding: ActivitySubCategoryBinding
    private lateinit var presenter: SubCategoryMVP.SubCategoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupNavDrawer()
    }

    private fun initView() {
        val catId: Int = intent.getIntExtra("catId", 0)
        val catName: String? = intent.getStringExtra("catName")
        binding.txtToolbar.text = catName
        presenter = SubCategoryPresenter(SubCategoryVolleyHandler(this), this)
        presenter.getSubCategories(catId)
        binding.imgCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupNavDrawer() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
            setDisplayShowTitleEnabled(false)
        }

        val logoutDialog = setupLogoutDialog()

        binding.navView.setNavigationItemSelectedListener { menuItems ->
            when (menuItems.itemId) {
                R.id.navLogout -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    logoutDialog.show()
                }
                R.id.navCart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                }
                R.id.navHome -> {
                    val intent = Intent(this, GroceryDashboardActivity::class.java)
                    startActivity(intent)
                }
                R.id.navOrderHistory -> {
                    val intent = Intent(this,AllOrdersActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    private fun setupLogoutDialog(): AlertDialog.Builder {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Logout?")
        alertDialog.setPositiveButton("Confirm") { _, _ ->
            val sharedPref =
                application.getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setNegativeButton("Cancel") { d, _ ->
            d.dismiss()
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

    override fun setSubCategoryResult(subCatResponse: SubCatResponse) {
        val catId: Int = intent.getIntExtra("catId", 0)

        if (catId == 1) {
            val fragmentManager =
                SubCatChickenFragmentAdapter(supportFragmentManager, subCatResponse.count)
            supportFragmentManager.beginTransaction()
            binding.viewPager.adapter = fragmentManager
            binding.tabLayout.apply {
                setupWithViewPager(binding.viewPager)
            }
            for (i in 0 until subCatResponse.count) {
                binding.tabLayout.getTabAt(i)?.text = subCatResponse.data[i].subName
            }

        } else if (catId == 2) {
            val fragmentManager =
                SubCatVegFragmentAdapter(supportFragmentManager, subCatResponse.count)
            supportFragmentManager.beginTransaction()
            binding.viewPager.adapter = fragmentManager
            binding.tabLayout.apply {
                setupWithViewPager(binding.viewPager)
            }
            for (i in 0 until subCatResponse.count) {
                binding.tabLayout.getTabAt(i)?.text = subCatResponse.data[i].subName
            }
        } else if (catId == 3) {
            val fragmentManager =
                SubCatFruitsFragmentAdapter(supportFragmentManager, subCatResponse.count)
            supportFragmentManager.beginTransaction()
            binding.viewPager.adapter = fragmentManager
            binding.tabLayout.apply {
                setupWithViewPager(binding.viewPager)
            }
            for (i in 0 until subCatResponse.count) {
                binding.tabLayout.getTabAt(i)?.text = subCatResponse.data[i].subName
            }
        } else if (catId == 4) {
            val fragmentManager =
                SubCatBeefFragmentAdapter(supportFragmentManager, subCatResponse.count)
            supportFragmentManager.beginTransaction()
            binding.viewPager.adapter = fragmentManager
            binding.tabLayout.apply {
                setupWithViewPager(binding.viewPager)
            }
            for (i in 0 until subCatResponse.count) {
                binding.tabLayout.getTabAt(i)?.text = subCatResponse.data[i].subName
            }
        }


    }

    override fun onSubCategoryLoad(isLoading: Boolean) {

    }
}