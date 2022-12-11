package com.example.groceryappprojectcharles.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.databinding.ActivitySplashScreenBinding
import com.example.groceryappprojectcharles.model.remote.Constants.EMAIL
import com.example.groceryappprojectcharles.model.remote.Constants.LOGIN_SHARED_PREF
import com.example.groceryappprojectcharles.model.remote.Constants.PASSWORD

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("tag", "onCreate SplashActivity")
        val startAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_start)
        binding.cvCart.startAnimation(startAnimation)

        val biggerTextAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_bigger)
        binding.txtAppName.startAnimation(biggerTextAnimation)

        Handler().postDelayed({
            if (application.getSharedPreferences(LOGIN_SHARED_PREF, Context.MODE_PRIVATE)
                    .contains(EMAIL) && application.getSharedPreferences(
                    LOGIN_SHARED_PREF,
                    Context.MODE_PRIVATE
                )
                    .contains(PASSWORD)
            ) {
                val intent = Intent(applicationContext, GroceryDashboardActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }, 2500)

    }
}
