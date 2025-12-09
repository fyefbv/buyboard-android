package com.example.buyboard_android.ui.auth.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buyboard_android.R
import com.example.buyboard_android.databinding.FragmentLoginBinding
import com.example.buyboard_android.ui.auth.AuthNavigationListener

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.registerLink.setOnClickListener {
            navigateToRegister()
        }

        binding.loginButton.setOnClickListener {
            navigateToMain()
        }
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToMain() {
        authNavigationListener.navigateToMain()
    }
}