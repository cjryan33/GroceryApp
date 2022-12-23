package com.example.groceryappprojectcharles.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.groceryappprojectcharles.databinding.FragmentRegistrationBinding
import com.example.groceryappprojectcharles.model.remote.data.RegisterData
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.RegistrationVolleyHandler
import com.example.groceryappprojectcharles.presenter.registration.RegistrationMVP
import com.example.groceryappprojectcharles.presenter.registration.RegistrationPresenter
import com.google.android.material.snackbar.Snackbar


class RegistrationFragment : Fragment() , RegistrationMVP.RegistrationView{
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var presenter: RegistrationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegistrationBinding.inflate(layoutInflater)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = binding.root
        presenter = RegistrationPresenter(RegistrationVolleyHandler(requireContext()),this)
        binding.apply {
            btnRegister.setOnClickListener {
                val name = edtName.text.toString()
                val phoneNo = edtPhoneNo.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val newUser = RegisterData(email,name,phoneNo,password)
                presenter.registerUser(newUser)
            }

        }
        return view
    }

    override fun setResult(message: String) {
        val snackBar = Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    override fun onLoad(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}