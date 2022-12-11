package com.example.groceryappprojectcharles.model.remote

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.groceryappprojectcharles.databinding.CategoryItemBinding
import com.example.groceryappprojectcharles.model.local.entity.Category
import com.example.groceryappprojectcharles.model.remote.Constants.BASE_IMAGE_URL
import com.example.groceryappprojectcharles.view.SubCategoryActivity

class CategoryAdapter(private val context: Context, private val categoryList: MutableList<Category>) :
    Adapter<CategoryAdapter.CategoryViewHolder>() {
    private lateinit var binding: CategoryItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = CategoryItemBinding.inflate(layoutInflater, parent, false)
        Log.i("tag","onCreateViewHolder")
        return CategoryViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Log.i("tag","BindViewHolder called")
        holder.apply {
            val category = categoryList[position]
            bind(category)

            itemView.setOnClickListener {
                val intent = Intent(context,SubCategoryActivity::class.java)
                intent.putExtra("category",category)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = categoryList.size

    inner class CategoryViewHolder(view: View) : ViewHolder(view) {
        fun bind(category: Category) {
            val url = BASE_IMAGE_URL + category.catImage
            try {
                Glide.with(context).load(url)
                    .into(binding.imgCategory)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            binding.txtCategory.text = category.catName

        }
    }
}