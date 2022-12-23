package com.example.groceryappprojectcharles.model.local.dao

import androidx.room.*
import com.example.groceryappprojectcharles.model.local.entity.SubCategory

@Dao
interface SubCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubCategory(subCategory: SubCategory)

    @Delete
    fun deleteSubCategory(subCategory: SubCategory)

    @Update
    fun updateSubCategory(subCategory: SubCategory)

    @Query("Select Count(subId) from SubCategory")
    fun getRowCount() : Int
}