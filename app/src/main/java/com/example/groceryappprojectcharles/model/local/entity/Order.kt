package com.example.groceryappprojectcharles.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(Converter::class)
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val orderTotal: Float,
    val payment: String,
    val address: String
)
