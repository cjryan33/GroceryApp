package com.example.groceryappprojectcharles.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappprojectcharles.databinding.FragmentFriedBinding
import com.example.groceryappprojectcharles.model.remote.adapters.SubCatAdapter
import com.example.groceryappprojectcharles.model.remote.data.ProductsBySubID
import com.example.groceryappprojectcharles.model.remote.response.ProductsBySubIDResponse
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.ProductBySubIdVolleyHandler
import com.example.groceryappprojectcharles.presenter.product_by_sub_id.ProductBySubIdMVP
import com.example.groceryappprojectcharles.presenter.product_by_sub_id.ProductBySubIdPresenter

class CombosFragment : Fragment(), ProductBySubIdMVP.ProductBySubIdView {
    private lateinit var binding: FragmentFriedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val presenter = ProductBySubIdPresenter(ProductBySubIdVolleyHandler(requireContext()),this)
        presenter.getProductsBySubId(3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentFriedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initView()
    }
    override fun setResult(productsBySubIDResponse: ProductsBySubIDResponse) {
        val productList = mutableListOf<ProductsBySubID>()
        if(productsBySubIDResponse.count!=0){
            for (i in productsBySubIDResponse.data.indices) {
                productList.add(productsBySubIDResponse.data[i])
            }
            val adapter = SubCatAdapter(requireContext(),productList)
            binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
            binding.rvItems.adapter = adapter
        } else {
            binding.rvItems.visibility = View.GONE
            binding.txtNoItems.visibility = View.VISIBLE
        }

    }

    override fun onLoad(isLoading: Boolean) {

    }
}