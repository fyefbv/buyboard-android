package com.example.buyboard_android.ui.auth.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buyboard_android.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var fragmentRegisterBinding: FragmentRegisterBinding
    private lateinit var listener: RegisterListener

    interface RegisterListener {
        fun onLoginClicked()
        fun onRegisterSuccessClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as RegisterListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return fragmentRegisterBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        fragmentRegisterBinding.loginLink.setOnClickListener {
            listener.onLoginClicked()
        }

        fragmentRegisterBinding.registerButton.setOnClickListener {
            listener.onRegisterSuccessClicked()
        }
    }
}