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
import com.example.groceryappprojectcharles.databinding.SearchItemBinding
import com.example.groceryappprojectcharles.model.local.AppDatabase
import com.example.groceryappprojectcharles.model.local.dao.CartDao
import com.example.groceryappprojectcharles.model.local.entity.Cart
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.data.ProductsBySubID
import com.google.android.material.snackbar.Snackbar

class SubCatAdapter(val context: Context, private val itemList: List<ProductsBySubID>) : Adapter<SubCatAdapter.SubCatViewHolder>(){
    private lateinit var binding: SearchItemBinding
    private var cartDao: CartDao? = null

    inner class SubCatViewHolder(view: View) : ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(product: ProductsBySubID) {
            binding.apply {
                txtDescription.text = product.description
                txtPrice.text = "\$%.2f".format(product.price)
                txtItemName.text = product.productName
                val url = Constants.BASE_IMAGE_URL + product.image
                try {
                    Glide.with(context).load(url)
                        .into(binding.imgItem)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCatViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = SearchItemBinding.inflate(layoutInflater,parent, false)
        return SubCatViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SubCatViewHolder, position: Int) {
        holder.apply {
            val product = itemList[position]
            bind(product)
        }
        buttonEvents(itemList[position])
    }
    private fun buttonEvents(productsBySubID: ProductsBySubID) {
        binding.apply {
            btnMinus.setOnClickListener {
                if (edtCount.text.toString().toInt() != 0) {
                    var temp = edtCount.text.toString().toInt()
                    temp--
                    edtCount.setText(temp.toString())
                }
            }
            btnPlus.setOnClickListener {
                var temp = edtCount.text.toString().toInt()
                temp++
                edtCount.setText(temp.toString())
            }

            btnAddToCart.setOnClickListener {
                addToCartDialog(edtCount.text.toString().toInt(),productsBySubID) }
        }
    }

    private fun addToCartDialog(numItem: Int, item: ProductsBySubID) {
        val cartDialog = AlertDialog.Builder(context)
        cartDialog
            .setTitle("Add $numItem ${item.productName} to your cart?")
            .setPositiveButton("Confirm") { _, _ ->
                addToCartConfirm(numItem, item)
                binding.edtCount.text?.clear()
                val snackBar = Snackbar.make(binding.root, "Added to Cart", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }
            .setNegativeButton("Cancel") {d,_ ->
                d.dismiss()
            }
            .show()
    }

    private fun addToCartConfirm(numItem: Int, item: ProductsBySubID) {
        initDB()
        val cartItem = Cart(0,item.productName,numItem,item.price, numItem*item.price, item.image)
        cartDao?.addItemToCart(cartItem)
        binding.edtCount.text?.clear()
    }

    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(context)
        cartDao = appDB?.getCartDao()
    }

    override fun getItemCount() = itemList.size
}