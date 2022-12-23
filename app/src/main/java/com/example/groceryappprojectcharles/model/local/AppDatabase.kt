package com.example.groceryappprojectcharles.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.groceryappprojectcharles.model.local.dao.*
import com.example.groceryappprojectcharles.model.local.entity.*

@Database(
    entities = [Category::class, SubCategory::class, Cart::class, Address::class, Order::class, Payment::class],
    version = 9,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getSubCategoryDao(): SubCategoryDao
    abstract fun getCartDao(): CartDao
    abstract fun getPaymentDao(): PaymentDao
    abstract fun getAddressDao(): AddressDao
    abstract fun getOrderDao(): OrderDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appDB"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}