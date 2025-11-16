package com.example.buyboard_android.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buyboard_android.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var listener: ProfileListener

    interface ProfileListener {
        fun onLogoutClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ProfileListener
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
            showSettingsFragment()
        }

        binding.logoutItem.setOnClickListener {
            listener.onLogoutClicked()
        }

        binding.editProfileButton.setOnClickListener {
            showEditProfileFragment()
        }
    }

    private fun showSettingsFragment() {
        val settingsFragment = SettingsFragment()
        settingsFragment.show(parentFragmentManager, "settings_dialog")
    }

    private fun showEditProfileFragment() {
        val editProfileFragment = EditProfileFragment()
        editProfileFragment.show(parentFragmentManager, "edit_profile_dialog")
    }
}