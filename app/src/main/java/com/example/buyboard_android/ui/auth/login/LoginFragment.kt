package com.example.buyboard_android.ui.auth.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buyboard_android.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var listener: LoginListener

    interface LoginListener {
        fun onRegisterClicked()
        fun onLoginSuccessClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as LoginListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return fragmentLoginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        fragmentLoginBinding.registerLink.setOnClickListener {
            listener.onRegisterClicked()
        }

        fragmentLoginBinding.loginButton.setOnClickListener {
            listener.onLoginSuccessClicked()
        }
    }
}