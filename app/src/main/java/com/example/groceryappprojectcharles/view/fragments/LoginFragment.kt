package com.example.groceryappprojectcharles.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.groceryappprojectcharles.databinding.FragmentLoginBinding
import com.example.groceryappprojectcharles.model.remote.Constants.EMAIL
import com.example.groceryappprojectcharles.model.remote.Constants.LOGIN_SHARED_PREF
import com.example.groceryappprojectcharles.model.remote.Constants.PASSWORD
import com.example.groceryappprojectcharles.model.remote.Constants.TOKEN
import com.example.groceryappprojectcharles.model.remote.Constants.USER_ID
import com.example.groceryappprojectcharles.model.remote.volleyhandlers.LoginVolleyHandler
import com.example.groceryappprojectcharles.model.remote.data.LoginData
import com.example.groceryappprojectcharles.model.remote.response.LoginResponse
import com.example.groceryappprojectcharles.presenter.login.LoginMVP
import com.example.groceryappprojectcharles.presenter.login.LoginPresenter
import com.example.groceryappprojectcharles.view.GroceryDashboardActivity
import com.google.android.material.snackbar.Snackbar

class   LoginFragment : Fragment(), LoginMVP.LoginView {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var presenter: LoginPresenter
    private lateinit var loginSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = binding.root
        presenter = LoginPresenter(LoginVolleyHandler(requireContext()), this)
        binding.apply {
            btnLoginFrag.setOnClickListener {
                val email = edtEmailLogin.text.toString()
                val password = edtPasswordLogin.text.toString()
                val user = LoginData(email, password)
                presenter.loginUser(user)
            }

        }

        return view
    }

    override fun setResult(message: String, loginResponse: LoginResponse) {
        if (message == "success") {
            loginSharedPreferences =
                requireContext().getSharedPreferences(LOGIN_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = loginSharedPreferences.edit()
            editor.putString(EMAIL, binding.edtEmailLogin.text.toString())
            editor.putString(PASSWORD, binding.edtPasswordLogin.text.toString())
            editor.putString(USER_ID, loginResponse.user._id)
            editor.putString(TOKEN, loginResponse.token)
            editor.apply()
            val intent = Intent(requireContext(), GroceryDashboardActivity::class.java)
            startActivity(intent)
        } else {
            val snackBar = Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_SHORT)
            snackBar.show()
        }

    }

    override fun onLoad(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}