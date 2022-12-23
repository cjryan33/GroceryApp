package com.example.groceryappprojectcharles.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.databinding.ActivityCartBinding
import com.example.groceryappprojectcharles.model.local.AppDatabase
import com.example.groceryappprojectcharles.model.local.dao.CartDao
import com.example.groceryappprojectcharles.model.local.entity.Cart
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.adapters.CartAdapter
import com.example.groceryappprojectcharles.model.remote.response.Product

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private var cartDao: CartDao? = null
    private lateinit var adapter: CartAdapter
    private var cartItems : MutableList<Cart>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavDrawer()
        initDB()
        setupButtons()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupButtons() {
        binding.apply {
            txtClearCart.setOnClickListener {
                val deleteDialog = android.app.AlertDialog.Builder(this@CartActivity)
                deleteDialog
                    .setTitle("Are you sure you want to clear your cart?")
                    .setPositiveButton("Confirm") { _, _ ->
                        cartDao?.deleteAllCartItems()
                        cartItems?.clear()
                        adapter.notifyDataSetChanged()
                        updateTotal()
                    }
                    .setNegativeButton("Cancel") { d, _ ->
                        d.dismiss()
                    }
                    .show()
            }

            btnCheckout.setOnClickListener {
                val productList = convertCartToProductList()
                val intent = Intent(baseContext, CheckoutActivity::class.java)
                intent.putExtra("productList",productList)
                intent.putExtra("total", cartDao?.getCartTotal())
                startActivity(intent)
            }

        }
    }

    private fun convertCartToProductList() : ArrayList<Product> {
        val cartItems = cartDao?.getCartItems()
        val productList = arrayListOf<Product>()
        for (i in cartItems?.indices!!) {
            val product = Product(i.toString(),cartItems[i].price,cartItems[i].productName,cartItems[i].quantity)
            productList.add(product)
        }
        return productList
    }

    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(this)
        cartDao = appDB?.getCartDao()
    }

    @SuppressLint("SetTextI18n")
    fun updateTotal() {
        val totalPrice = cartDao?.getCartTotal()
        binding.txtTotalPrice.text = "Total: $" + "%.2f".format(totalPrice)
    }

    override fun onResume() {
        super.onResume()
        cartItems = cartDao?.getCartItems()
        updateTotal()
        adapter = cartItems?.let { CartAdapter(this, it) }!!
        binding.apply {
            rvCart.layoutManager = LinearLayoutManager(this@CartActivity)
            rvCart.adapter = adapter
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