package com.example.buyboard_android.ui.auth.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.R
import com.example.buyboard_android.databinding.FragmentLoginBinding
import com.example.buyboard_android.ui.auth.AuthNavigationListener
import com.example.buyboard_android.data.network.services.AuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            attemptLogin()
        }

        binding.passwordEditText.setOnEditorActionListener { _, _, _ ->
            attemptLogin()
            true
        }
    }

    private fun attemptLogin() {
        val login = binding.loginEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (login.isEmpty()) {
            binding.loginInputLayout.error = "Введите логин"
            return
        } else {
            binding.loginInputLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Введите пароль"
            return
        } else {
            binding.passwordInputLayout.error = null
        }

        showLoading(true)

        val authService = (requireActivity().application as BuyBoardApp).authService

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    authService.login(login, password)
                }

                navigateToMain()
            } catch (e: Exception) {
                showError(e.message ?: "Ошибка входа")
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loginButton.isEnabled = !isLoading
        binding.loginEditText.isEnabled = !isLoading
        binding.passwordEditText.isEnabled = !isLoading
        binding.registerLink.isEnabled = !isLoading
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

        binding.passwordEditText.text?.clear()
        binding.passwordInputLayout.error = message
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToMain() {
        authNavigationListener.navigateToMain()
    }
}