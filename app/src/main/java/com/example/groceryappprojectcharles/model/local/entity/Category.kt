package com.example.groceryappprojectcharles.model.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Category(
    val catDescription: String,
    @PrimaryKey val catId: Int,
    val catImage: String,
    val catName: String,
) : Parcelable