package com.example.buyboard_android.ui.auth.register

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.R
import com.example.buyboard_android.databinding.FragmentRegisterBinding
import com.example.buyboard_android.ui.auth.AuthNavigationListener
import com.example.buyboard_android.data.network.services.AuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        setupTextChangeListeners()
        setupClickListeners()
    }

    private fun setupTextChangeListeners() {
        binding.loginEditText.addTextChangedListener {
            binding.loginInputLayout.error = null
        }

        binding.emailEditText.addTextChangedListener {
            binding.emailInputLayout.error = null
        }

        binding.passwordEditText.addTextChangedListener {
            binding.passwordInputLayout.error = null
            validatePasswordMatch()
        }

        binding.confirmPasswordEditText.addTextChangedListener {
            binding.confirmPasswordInputLayout.error = null
            validatePasswordMatch()
        }
    }

    private fun setupClickListeners() {
        binding.loginLink.setOnClickListener {
            navigateToLogin()
        }

        binding.registerButton.setOnClickListener {
            attemptRegistration()
        }
    }

    private fun attemptRegistration() {
        val login = binding.loginEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        var hasError = false

        if (login.isEmpty()) {
            binding.loginInputLayout.error = "Введите логин"
            hasError = true
        } else if (login.length < 3) {
            binding.loginInputLayout.error = "Логин должен быть не менее 3 символов"
            hasError = true
        }

        if (email.isEmpty()) {
            binding.emailInputLayout.error = "Введите email"
            hasError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.error = "Введите корректный email"
            hasError = true
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Введите пароль"
            hasError = true
        } else if (password.length < 6) {
            binding.passwordInputLayout.error = "Пароль должен быть не менее 6 символов"
            hasError = true
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordInputLayout.error = "Подтвердите пароль"
            hasError = true
        } else if (password != confirmPassword) {
            binding.confirmPasswordInputLayout.error = "Пароли не совпадают"
            hasError = true
        }

        if (hasError) return

        showLoading(true)

        val authService = (requireActivity().application as BuyBoardApp).authService

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    authService.register(login, email, password)
                }

                Toast.makeText(
                    requireContext(),
                    "Регистрация успешна! Выполняется вход...",
                    Toast.LENGTH_SHORT
                ).show()

                navigateToMain()
            } catch (e: Exception) {
                showError(e.message ?: "Ошибка регистрации")
            } finally {
                showLoading(false)
            }
        }
    }

    private fun validatePasswordMatch() {
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        if (confirmPassword.isNotEmpty() && password != confirmPassword) {
            binding.confirmPasswordInputLayout.error = "Пароли не совпадают"
        } else {
            binding.confirmPasswordInputLayout.error = null
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.registerButton.isEnabled = !isLoading
        binding.loginEditText.isEnabled = !isLoading
        binding.emailEditText.isEnabled = !isLoading
        binding.passwordEditText.isEnabled = !isLoading
        binding.confirmPasswordEditText.isEnabled = !isLoading
        binding.loginLink.isEnabled = !isLoading
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

        binding.passwordEditText.text?.clear()
        binding.confirmPasswordEditText.text?.clear()
    }

    private fun navigateToLogin() {
        findNavController().navigateUp()
    }

    private fun navigateToMain() {
        authNavigationListener.navigateToMain()
    }
}