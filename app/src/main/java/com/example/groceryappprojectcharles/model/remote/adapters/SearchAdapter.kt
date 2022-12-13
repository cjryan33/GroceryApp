package com.example.groceryappprojectcharles.model.remote.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.groceryappprojectcharles.databinding.SearchItemBinding
import com.example.groceryappprojectcharles.model.remote.Constants
import com.example.groceryappprojectcharles.model.remote.data.SearchData

class SearchAdapter(val context: Context,val searchList: List<SearchData>) : Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var binding: SearchItemBinding
    inner class SearchViewHolder(view:View) : ViewHolder(view) {
        fun bind(searchItem: SearchData) {
            binding.apply {
                txtDescription.text = searchItem.description
                txtPrice.text = "\$${searchItem.price}"
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
        binding = SearchItemBinding.inflate(layoutInflater,parent,false)
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.apply {
            val searchItem = searchList[position]
            bind(searchItem)
        }
    }

    override fun getItemCount() = searchList.size
}