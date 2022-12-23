package com.example.groceryappprojectcharles.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubCategory(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val position: Int,
    val status: Boolean,
    val subDescription: String,
    val subId: Int,
    val subImage: String,
    @PrimaryKey val subName: String
)
