package com.example.groceryappprojectcharles.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.groceryappprojectcharles.model.local.entity.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = REPLACE)
    fun insertCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)

    @Update
    fun updateCategory(category: Category)

    @Query("Select * from Category")
    fun getAllCategories():MutableList<Category>
}