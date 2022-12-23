package com.example.groceryappprojectcharles.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Payment(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nickname: String,
    val cardNumber: String,
    val cvv: Int,
    val expDate: String
)
