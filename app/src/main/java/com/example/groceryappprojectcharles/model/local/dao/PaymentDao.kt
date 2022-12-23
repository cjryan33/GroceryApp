package com.example.groceryappprojectcharles.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.groceryappprojectcharles.model.local.entity.Payment

@Dao
interface PaymentDao {

    @Insert
    fun addPayment(payment: Payment)

    @Delete
    fun deletePayment(payment: Payment)

    @Update
    fun updatePayment(payment: Payment)

    @Query("delete from Payment")
    fun deleteAllPayments()

    @Query("select * from Payment")
    fun getAllPayments(): List<Payment>
}
