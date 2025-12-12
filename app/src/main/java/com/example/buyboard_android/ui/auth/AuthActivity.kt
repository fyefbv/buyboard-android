package com.example.buyboard_android.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.R
import com.example.buyboard_android.databinding.ActivityAuthBinding
import com.example.buyboard_android.ui.main.MainActivity

class AuthActivity : AppCompatActivity(), AuthNavigationListener {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app = application as BuyBoardApp

        if (app.authService.isLoggedIn()) {
            navigateToMain()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment

        return navHostFragment.navController.navigateUp()
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}