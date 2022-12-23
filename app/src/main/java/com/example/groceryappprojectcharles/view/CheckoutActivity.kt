package com.example.groceryappprojectcharles.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.databinding.ActivityCheckoutBinding
import com.example.groceryappprojectcharles.databinding.AddAddressDialogBinding
import com.example.groceryappprojectcharles.databinding.AddPaymentDialogBinding
import com.example.groceryappprojectcharles.model.local.AppDatabase
import com.example.groceryappprojectcharles.model.local.dao.AddressDao
import com.example.groceryappprojectcharles.model.local.dao.OrderDao
import com.example.groceryappprojectcharles.model.local.dao.PaymentDao
import com.example.groceryappprojectcharles.model.local.entity.Address
import com.example.groceryappprojectcharles.model.local.entity.Order
import com.example.groceryappprojectcharles.model.local.entity.Payment
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.adapters.AddressSpinnerAdapter
import com.example.groceryappprojectcharles.model.remote.adapters.PaymentSpinnerAdapter
import com.example.groceryappprojectcharles.model.remote.response.*
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.AddressVolleyHandler
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.OrderVolleyHandler
import com.example.groceryappprojectcharles.presenter.address.AddressMVP
import com.example.groceryappprojectcharles.presenter.address.AddressPresenter
import com.example.groceryappprojectcharles.presenter.order.OrderMVP
import com.example.groceryappprojectcharles.presenter.order.OrderPresenter
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar

class CheckoutActivity : AppCompatActivity(), AddressMVP.AddressView, OrderMVP.OrderView {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var addPaymentBinding: AddPaymentDialogBinding
    private lateinit var addAddressBinding: AddAddressDialogBinding
    private lateinit var addressAdapter: AddressSpinnerAdapter
    private lateinit var paymentAdapter: PaymentSpinnerAdapter
    private var addressDao: AddressDao? = null
    private var paymentDao: PaymentDao? = null
    private var orderDao: OrderDao? = null
    private lateinit var paymentList: MutableList<Payment>
    private lateinit var addressList: MutableList<Address>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val total = intent.getFloatExtra("total", 0f)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        addPaymentBinding = AddPaymentDialogBinding.inflate(layoutInflater)
        addAddressBinding = AddAddressDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtTotalPrice.text = "Total: \$%.2f".format(total)
        initDB()
        setupEvents()
        setupNavDrawer()
    }

    private fun setupEvents() {
        binding.txtAddPayment.setOnClickListener {
            addPaymentDialog()
        }
        binding.txtAddAddress.setOnClickListener {
            addAddressDialog()
        }
        binding.btnPlaceOrder.setOnClickListener {
            placeOrderDialog()
        }
        paymentList = (paymentDao?.getAllPayments() as MutableList<Payment>?)!!
        addressList = addressDao?.getAllAddresses() as MutableList<Address>
        paymentAdapter = PaymentSpinnerAdapter(this, paymentList)
        addressAdapter = AddressSpinnerAdapter(this, addressList)
        binding.spnPaymentInfo.adapter = paymentAdapter
        binding.spnAddressInfo.adapter = addressAdapter
    }

    private fun placeOrderDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog
            .setPositiveButton("Place Order") { _, _ ->
                val total = intent.getFloatExtra("total", 0f)
                val sharedPreferences = getSharedPreferences(Constants.LOGIN_SHARED_PREF,Context.MODE_PRIVATE)
                val payment = binding.spnPaymentInfo.selectedItem as Payment
                val address = binding.spnAddressInfo.selectedItem as Address
                val productList = intent.getParcelableArrayListExtra<Parcelable>("productList")
                val shippingAddress = ShippingAddress(
                    address.city,
                    address.houseNo,
                    address.zip,
                    address.street,
                    address.type
                )
                val products = mutableListOf<Product>()
                for (i in productList?.indices!!) {
                    products.add(productList[i] as Product)
                }
                val orderSummary = OrderSummary("1",10,10,10,total)
                val user = sharedPreferences.getString(Constants.USER_ID,"")
                    ?.let { User(it, sharedPreferences.getString(Constants.EMAIL,"")!!) }
                val newOrder = Order(
                    0,
                    total,
                    payment.nickname,
                    address.nickname
                )
                val sdf = SimpleDateFormat("dd/mm/yyyy")
                val date = sdf.format(Calendar.DATE)
                val orderData = OrderData(0,"0",date,"incomplete",orderSummary,products,shippingAddress,
                    user!!,
                    sharedPreferences.getString(Constants.USER_ID,"")!!
                )
                val presenter = OrderPresenter(OrderVolleyHandler(this), this)
                presenter.addOrder(orderData)
                orderDao?.addOrder(newOrder)
            }
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
            .setTitle("Are you sure you want to place this order?")
            .show()
    }

    private fun addAddressDialog() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setView(addAddressBinding.root)
        dialog.setPositiveButton("Add") { _, _ ->
            val newAddress = Address(
                0,
                addAddressBinding.edtNickname.text.toString(),
                addAddressBinding.edtHouseNo.text.toString(),
                addAddressBinding.edtStreet.text.toString(),
                addAddressBinding.edtCity.text.toString(),
                addAddressBinding.edtType.text.toString(),
                addAddressBinding.edtState.text.toString(),
                addAddressBinding.edtZipcode.text.toString().toInt()
            )
            val presenter = AddressPresenter(AddressVolleyHandler(applicationContext), this)
            presenter.addAddress(newAddress)
            addressDao?.addAddress(newAddress)
            addressList.add(newAddress)
            addressAdapter.notifyDataSetChanged()
        }
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
            .setTitle("Add Address")
            .show()
    }

    private fun addPaymentDialog() {
        binding.spnPaymentInfo.adapter = paymentAdapter
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setView(addPaymentBinding.root)
        dialog.setPositiveButton("Add") { _, _ ->
            val newPayment = Payment(
                0,
                addPaymentBinding.edtNickname.text.toString(),
                addPaymentBinding.edtCardNumber.text.toString(),
                addPaymentBinding.edtCVV.text.toString().toInt(),
                addPaymentBinding.edtExpDate.text.toString()
            )
            paymentDao?.addPayment(newPayment)
            paymentList.add(newPayment)
            paymentAdapter.notifyDataSetChanged()
        }
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
            .setTitle("Add Payment")
            .show()


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
                    val intent = Intent(this, AllOrdersActivity::class.java)
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

    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(this)
        paymentDao = appDB?.getPaymentDao()
        addressDao = appDB?.getAddressDao()
        orderDao = appDB?.getOrderDao()
    }

    override fun setResult(message: String) {
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    override fun onLoad(isLoading: Boolean) {
        //do nothing
    }

    override fun setOrderResult(message: String) {
        if (message != "ok") {
            val snackBar =
                Snackbar.make(binding.root, "Something Went Wrong", Snackbar.LENGTH_SHORT)
            snackBar.show()
        } else {
            val snackBar = Snackbar.make(binding.root, "Order Placed", Snackbar.LENGTH_SHORT)
            snackBar.show()
        }
        val intent = Intent(this, OrderConfirmedActivity::class.java)
        startActivity(intent)
    }

    override fun setOrderResult(orderResponse: OrderResponse) {
        if (orderResponse.error) {
            val snackBar =
                Snackbar.make(binding.root, "Something Went Wrong", Snackbar.LENGTH_SHORT)
            snackBar.show()
        } else {
            val snackBar = Snackbar.make(binding.root, "Order Placed", Snackbar.LENGTH_SHORT)
            snackBar.show()
        }
        val intent = Intent(this, OrderConfirmedActivity::class.java)
        startActivity(intent)
    }
}