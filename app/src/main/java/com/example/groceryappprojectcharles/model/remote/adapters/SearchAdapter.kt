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
import com.example.groceryappprojectcharles.model.remote.data.SearchData
import com.google.android.material.snackbar.Snackbar

class SearchAdapter(val context: Context, private val searchList: List<SearchData>) :
    Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var binding: SearchItemBinding
    private var cartDao: CartDao? = null

    inner class SearchViewHolder(view: View) : ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(searchItem: SearchData) {
            binding.apply {
                txtDescription.text = searchItem.description
                txtPrice.text = "\$ %.2f".format(searchItem.price)
                txtItemName.text = searchItem.productName
                val url = Constants.BASE_IMAGE_URL + searchItem.image
                try {
                    Glide.with(context).load(url)
                        .into(binding.imgItem)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = SearchItemBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.apply {
            val searchItem = searchList[position]
            bind(searchItem)
        }
        buttonEvents(searchList[position])
    }

    private fun buttonEvents(searchData: SearchData) {
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
                addToCartDialog(edtCount.text.toString().toInt(), searchData)
            }
        }
    }

    private fun addToCartDialog(numItem: Int, item: SearchData) {
        val cartDialog = AlertDialog.Builder(context)
        val convert = ProductsBySubID(
            item.__v,
            item._id,
            item.catId,
            item.created,
            item.description,
            item.image,
            item.mrp.toFloat(),
            item.position,
            item.price.toFloat(),
            item.productName,
            item.quantity,
            item.status,
            item.subId,
            item.unit
        )
        cartDialog
            .setTitle("Add $numItem ${item.productName} to your cart?")
            .setPositiveButton("Confirm") { _, _ ->
                addToCartConfirm(numItem, convert)
                binding.edtCount.text?.clear()
                val snackBar = Snackbar.make(binding.root, "Added to Cart", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
            .show()
    }

    private fun addToCartConfirm(numItem: Int, item: ProductsBySubID) {
        initDB()
        val cartItem =
            Cart(0, item.productName, numItem, item.price, numItem * item.price, item.image)
        cartDao?.addItemToCart(cartItem)
    }
    private fun initDB() {
        val appDB: AppDatabase? = AppDatabase.getInstance(context)
        cartDao = appDB?.getCartDao()
    }


    override fun getItemCount() = searchList.size
}