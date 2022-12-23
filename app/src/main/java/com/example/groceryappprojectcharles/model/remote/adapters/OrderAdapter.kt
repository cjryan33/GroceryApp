package com.example.groceryappprojectcharles.model.remote.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.groceryappprojectcharles.databinding.OrderItemBinding
import com.example.groceryappprojectcharles.model.remote.response.OrderData
import java.text.SimpleDateFormat

class OrderAdapter(
    val context: Context,
    private val orderList: List<OrderData>
) : Adapter<OrderAdapter.OrderViewHolder>() {
    private lateinit var binding: OrderItemBinding

    inner class OrderViewHolder(view: View) : ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(order: OrderData) {
            binding.apply {
                txtId.text = "Order #: " + order._id
                txtAddress.text =
                    "${order.shippingAddress.houseNo} ${order.shippingAddress.streetName} ${order.shippingAddress.city} ${order.shippingAddress.pincode}"
                val year = order.date.substring(0,4)
                val month = order.date.substring(5,7)
                val day = order.date.substring(8,10)
                txtDate.text = "$month/$day/$year"
                txtTotal.text = "Total: \$%.2f".format(order.orderSummary.totalAmount)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val lf = LayoutInflater.from(context)
        binding = OrderItemBinding.inflate(lf, parent, false)
        return OrderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.apply {
            val order = orderList[position]
            bind(order)
        }
    }

    override fun getItemCount() = orderList.size

}