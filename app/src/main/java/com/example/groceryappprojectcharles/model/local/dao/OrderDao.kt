package com.example.groceryappprojectcharles.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.groceryappprojectcharles.model.local.entity.Order

@Dao
interface OrderDao {
    @Insert
    fun addOrder(order: Order)

    @Delete
    fun deleteOrder(order:Order)

    @Query("select * from `Order`")
    fun getAllOrders(): List<Order>
}