package com.example.buyboard_android.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.buyboard_android.R
import com.example.buyboard_android.databinding.ActivityMainBinding
import com.example.buyboard_android.ui.auth.AuthActivity
import com.example.buyboard_android.ui.favorites.FavoritesFragment.FavoritesListener
import com.example.buyboard_android.ui.home.AdDetailsFragment
import com.example.buyboard_android.ui.home.AdDetailsFragment.AdDetailsListener
import com.example.buyboard_android.ui.home.HomeFragment.HomeListener
import com.example.buyboard_android.ui.profile.ProfileFragment.ProfileListener

class MainActivity : AppCompatActivity(),
    ProfileListener,
    HomeListener,
    FavoritesListener,
    AdDetailsListener {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.root.post {
            setupNavigation()
        }
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)

        activityMainBinding.bottomNavigation.setupWithNavController(navController)
    }

    private fun navigateToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLogoutClicked() {
        navigateToAuth()
    }

    override fun onFavoriteAdClicked() {
        val fragment = AdDetailsFragment()
        supportFragmentManager.beginTransaction()
            .replace(activityMainBinding.navHostFragment.id, fragment)
            .addToBackStack("favorites")
            .commit()
    }

    override fun onHomeAdClicked() {
        val fragment = AdDetailsFragment()
        supportFragmentManager.beginTransaction()
            .replace(activityMainBinding.navHostFragment.id, fragment)
            .addToBackStack("home")
            .commit()
    }

    override fun onBackClicked() {
        supportFragmentManager.popBackStack()
    }
}