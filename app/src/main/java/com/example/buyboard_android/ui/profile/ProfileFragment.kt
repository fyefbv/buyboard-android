package com.example.buyboard_android.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buyboard_android.R
import com.example.buyboard_android.databinding.FragmentProfileBinding
import com.example.buyboard_android.ui.main.MainNavigationListener

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var mainNavigationListener: MainNavigationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainNavigationListener) {
            mainNavigationListener = context
        } else {
            throw RuntimeException("$context must implement MainNavigationListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.settingsItem.setOnClickListener {
            showSettingsDialog()
        }

        binding.logoutItem.setOnClickListener {
            navigateToAuth()
        }

        binding.myAdsItem.setOnClickListener {
            navigateToMyAds()
        }

        binding.editProfileButton.setOnClickListener {
            showEditProfileDialog()
        }
    }

    private fun showSettingsDialog() {
        findNavController().navigate(R.id.action_nav_profile_to_settingsDialog)
    }

    private fun showEditProfileDialog() {
        findNavController().navigate(R.id.action_nav_profile_to_editProfileDialog)
    }

    private fun navigateToMyAds() {
        findNavController().navigate(R.id.action_nav_profile_to_myAdsFragment)
    }

    private fun navigateToAuth() {
        mainNavigationListener.navigateToAuth()
    }
}