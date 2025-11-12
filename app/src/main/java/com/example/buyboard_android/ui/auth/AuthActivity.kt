package com.example.buyboard_android.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buyboard_android.databinding.ActivityAuthBinding
import com.example.buyboard_android.ui.auth.login.LoginFragment
import com.example.buyboard_android.ui.auth.register.RegisterFragment
import com.example.buyboard_android.ui.main.MainActivity

class AuthActivity : AppCompatActivity(),
    LoginFragment.LoginListener,
    RegisterFragment.RegisterListener {
    private lateinit var activityAuthBinding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAuthBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(activityAuthBinding.root)

        showLoginFragment()
    }

    private fun showLoginFragment() {
        val fragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .replace(activityAuthBinding.authFragmentContainer.id, fragment)
            .commit()
    }

    private fun showRegisterFragment() {
        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
            .replace(activityAuthBinding.authFragmentContainer.id, fragment)
            .addToBackStack("register")
            .commit()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRegisterClicked() {
        showRegisterFragment()
    }

    override fun onLoginClicked() {
        supportFragmentManager.popBackStack()
    }

    override fun onRegisterSuccessClicked() {
        navigateToMain()
    }

    override fun onLoginSuccessClicked() {
        navigateToMain()
    }
}