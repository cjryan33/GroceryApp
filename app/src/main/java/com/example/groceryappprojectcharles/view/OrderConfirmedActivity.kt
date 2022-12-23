package com.example.groceryappprojectcharles.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.databinding.ActivityOrderConfirmedBinding
import com.example.groceryappprojectcharles.model.local.AppDatabase
import com.example.groceryappprojectcharles.model.local.dao.CartDao
import com.example.groceryappprojectcharles.model.remote.Constants

class OrderConfirmedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderConfirmedBinding
    private var cartDao : CartDao? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmedBinding.inflate(layoutInflater)
        initDB()
        setContentView(binding.root)
        setupNavDrawer()
        cartDao?.deleteAllCartItems()
    }
    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(applicationContext)
        cartDao = appDB?.getCartDao()
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
}