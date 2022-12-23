package com.example.groceryappprojectcharles.model.local.dao

import androidx.room.*
import com.example.groceryappprojectcharles.model.local.entity.Address

@Dao
interface AddressDao {
    @Insert
    fun addAddress(address: Address)

    @Delete
    fun deleteAddress(address: Address)

    @Update
    fun updateAddress(address: Address)

    @Query("select * from Address")
    fun getAllAddresses(): List<Address>
}