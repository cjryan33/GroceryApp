package com.example.groceryappprojectcharles.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nickname: String,
    val houseNo: String,
    val street: String,
    val city: String,
    val type: String,
    val state: String,
    val zip: Int
)
