package com.example.buyboard_android.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buyboard_android.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var fragmentFavoritesBinding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return fragmentFavoritesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}