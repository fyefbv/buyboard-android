package com.example.buyboard_android.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.buyboard_android.R
import com.example.buyboard_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
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
}