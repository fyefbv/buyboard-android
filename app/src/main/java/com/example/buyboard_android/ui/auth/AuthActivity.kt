package com.example.buyboard_android.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buyboard_android.databinding.ActivityAuthBinding
import com.example.buyboard_android.ui.auth.login.LoginFragment
import com.example.buyboard_android.ui.auth.register.RegisterFragment

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

    override fun onRegisterClicked() {
        showRegisterFragment()
    }

    override fun onLoginClicked() {
        supportFragmentManager.popBackStack()
    }
}