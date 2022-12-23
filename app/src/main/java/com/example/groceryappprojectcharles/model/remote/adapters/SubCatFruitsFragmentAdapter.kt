package com.example.groceryappprojectcharles.model.remote.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.groceryappprojectcharles.view.fragments.FruitsFragment
import com.example.groceryappprojectcharles.view.fragments.VegetablesFragment

class SubCatFruitsFragmentAdapter(fm: FragmentManager, private val subCount:Int) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return subCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0-> FruitsFragment()
            1 -> VegetablesFragment()
            else -> VegetablesFragment()
        }

    }
}