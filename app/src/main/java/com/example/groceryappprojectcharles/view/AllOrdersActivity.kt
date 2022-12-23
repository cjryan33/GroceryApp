package com.example.groceryappprojectcharles.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.databinding.ActivityAllOrdersBinding
import com.example.groceryappprojectcharles.model.local.AppDatabase
import com.example.groceryappprojectcharles.model.local.dao.OrderDao
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.adapters.OrderAdapter
import com.example.groceryappprojectcharles.model.remote.response.OrderData
import com.example.groceryappprojectcharles.model.remote.response.OrderResponse
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.OrderVolleyHandler
import com.example.groceryappprojectcharles.presenter.order.OrderMVP
import com.example.groceryappprojectcharles.presenter.order.OrderPresenter
import org.json.JSONObject

class AllOrdersActivity : AppCompatActivity(), OrderMVP.OrderView {
    private lateinit var binding: ActivityAllOrdersBinding
    private var orderDao: OrderDao? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtToolbar.text = getString(R.string.order_history_text)
        setupNavDrawer()
        setupView()
    }

    private fun setupView() {
        val presenter = OrderPresenter(OrderVolleyHandler(this), this)
        presenter.getOrders()
        initDB()
    }

    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(this)
        orderDao = appDB?.getOrderDao()
    }

    private fun setupNavDrawer() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
            setDisplayShowTitleEnabled(false)
        }
        binding.imgCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
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

    override fun setOrderResult(message: String) {
        //do nothing
    }

    override fun setOrderResult(orderResponse: OrderResponse) {
        val adapter = OrderAdapter(this,orderResponse.data)
        binding.apply {
            rvOrders.layoutManager = LinearLayoutManager(this@AllOrdersActivity)
            rvOrders.adapter = adapter
        }
    }
}