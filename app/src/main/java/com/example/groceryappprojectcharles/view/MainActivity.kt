package com.example.groceryappprojectcharles.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.databinding.ActivityMainBinding
import com.example.groceryappprojectcharles.presenter.registration.RegistrationMVP
import com.example.groceryappprojectcharles.view.fragments.LoginFragment
import com.example.groceryappprojectcharles.view.fragments.RegistrationFragment

class MainActivity : AppCompatActivity(), RegistrationMVP.RegistrationView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var regFragment: RegistrationFragment
    private lateinit var loginFragment: LoginFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.flLogin,loginFragment).commit()
        setUpEvents()
    }

    private fun setUpEvents() {
       binding.apply {
           btnRegister.setOnClickListener {
               btnLogin.isActivated = false
               regFragment = RegistrationFragment()
               supportFragmentManager.beginTransaction().replace(R.id.flLogin,regFragment).commit()
           }
           btnLogin.setOnClickListener {
               btnRegister.isActivated = false
               supportFragmentManager.beginTransaction().replace(R.id.flLogin,loginFragment).commit()
           }

       }
    }

    override fun setResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoad(isLoading: Boolean) {

    }
}