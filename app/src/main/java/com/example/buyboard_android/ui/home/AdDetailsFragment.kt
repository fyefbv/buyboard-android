package com.example.buyboard_android.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buyboard_android.databinding.FragmentAdDetailsBinding

class AdDetailsFragment : Fragment() {
    private lateinit var binding: FragmentAdDetailsBinding
    private lateinit var listener: AdDetailsListener

    interface AdDetailsListener {
        fun onBackClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as AdDetailsListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            listener.onBackClicked()
        }
    }
}