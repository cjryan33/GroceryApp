package com.example.groceryappprojectcharles.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.groceryappprojectcharles.model.local.dao.CategoryDao
import com.example.groceryappprojectcharles.model.local.entity.Category

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getCategoryDao() :CategoryDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appDB"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}