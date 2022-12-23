package com.example.groceryappprojectcharles.model.remote.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.groceryappprojectcharles.databinding.CartItemBinding
import com.example.groceryappprojectcharles.model.local.AppDatabase
import com.example.groceryappprojectcharles.model.local.dao.CartDao
import com.example.groceryappprojectcharles.model.local.entity.Cart
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.view.CartActivity

class CartAdapter(private val context: Context, private val cartItems: MutableList<Cart>) :
    Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var binding: CartItemBinding
    private var cartDao: CartDao? = null
    private var con = context

    inner class CartViewHolder(view: View) : ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Cart) {
            binding.apply {
                txtName.text = item.productName
                txtUnitPrice.text = "\$%.2f".format(item.price)
                txtBulkPrice.text = "\$%.2f".format(item.totalPrice)
                txtQuantity.text = item.quantity.toString()
                val url = Constants.BASE_IMAGE_URL + item.productImg
                try {
                    Glide.with(context).load(url)
                        .into(binding.imgItem)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val lf = LayoutInflater.from(context)
        binding = CartItemBinding.inflate(lf, parent, false)
        return CartViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.apply {
            initDB()
            val item = cartItems[position]
            bind(item)
            val deleteDialog = AlertDialog.Builder(context)
            binding.txtDelete.setOnClickListener {
                deleteDialog
                    .setTitle("Are you sure you want to delete this item from your Cart?")
                    .setPositiveButton("Confirm") { _, _ ->
                        cartDao?.deleteItemFromCart(item)
                        cartItems.removeAt(position)
                        notifyItemRemoved(position)
                        (con as CartActivity).updateTotal()
                    }
                    .setNegativeButton("Cancel") { d, _ ->
                        d.dismiss()
                    }
                    .show()
            }

        }
    }

    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(context)
        cartDao = appDB?.getCartDao()
    }


    override fun getItemCount() = cartItems.size
}