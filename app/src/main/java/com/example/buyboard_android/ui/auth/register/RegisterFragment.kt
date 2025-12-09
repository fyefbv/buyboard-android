package com.example.buyboard_android.ui.auth.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buyboard_android.databinding.FragmentRegisterBinding
import com.example.buyboard_android.ui.auth.AuthNavigationListener

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var authNavigationListener: AuthNavigationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AuthNavigationListener) {
            authNavigationListener = context
        } else {
            throw RuntimeException("$context must implement AuthNavigationListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.loginLink.setOnClickListener {
            navigateToLogin()
        }

        binding.registerButton.setOnClickListener {
            navigateToMain()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigateUp()
    }

    private fun navigateToMain() {
        authNavigationListener.navigateToMain()
    }
}