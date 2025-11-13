package com.example.buyboard_android.ui.create_ad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buyboard_android.databinding.FragmentCreateAdBinding

class CreateAdFragment : Fragment() {
    private lateinit var fragmentCreateAdBinding: FragmentCreateAdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCreateAdBinding = FragmentCreateAdBinding.inflate(inflater, container, false)
        return fragmentCreateAdBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}