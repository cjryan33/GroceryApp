package com.example.groceryappprojectcharles.model.local.entity

import androidx.room.TypeConverter

class Converter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun toFloat(num: Int) = num.toFloat()

        @TypeConverter
        @JvmStatic
        fun toInt(num:Float) = num.toInt()
    }
}