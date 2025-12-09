package com.example.buyboard_android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.buyboard_android.R
import com.example.buyboard_android.databinding.FragmentEditProfileBinding

class EditProfileFragment : DialogFragment() {
    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawableResource(R.drawable.dialog_rounded_background_with_margin)
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun setupClickListeners() {
        binding.saveButton.setOnClickListener {
            dismissDialog()
        }

        binding.cancelButton.setOnClickListener {
            dismissDialog()
        }
    }

    private fun dismissDialog() {
        findNavController().navigateUp()
    }
}