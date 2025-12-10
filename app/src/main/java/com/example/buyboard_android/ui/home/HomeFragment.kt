package com.example.buyboard_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buyboard_android.R
import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.data.models.domain.Category
import com.example.buyboard_android.data.models.domain.Location
import com.example.buyboard_android.databinding.FragmentHomeBinding
import com.example.buyboard_android.ui.home.adapters.AdsAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adsAdapter: AdsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefresh()
        setupClickListeners()
        loadAds()
    }

    private fun setupRecyclerView() {
        binding.adsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adsAdapter = AdsAdapter(
                onAdClick = { ad ->
                    navigateToAdDetails(ad)
                },
                onFavoriteClick = { ad ->
                    toggleFavorite(ad)
                }
            )
            adapter = adsAdapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                binding.swipeRefreshLayout.isRefreshing = false
                loadAds()
            }
            setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.primary_500)
            )
        }
    }

    private fun setupClickListeners() {
        binding.filterButton.setOnClickListener {
            showCategoriesDialog()
        }
    }

    private fun loadAds() {
        val mockAds = listOf(
            Ad(
                id = "1",
                userId = "1",
                title = "Смартфон Samsung Galaxy S21",
                description = "Отличное состояние",
                price = 25000.0,
                category = Category("1", "Электроника"),
                location = Location("1", "Москва"),
                createdAt = "2025-11-10 15:34",
                updatedAt = "2025-11-10 15:34"
            ),
            Ad(
                id = "1",
                userId = "1",
                title = "Смартфон Samsung Galaxy S21",
                description = "Отличное состояние",
                price = 25000.0,
                category = Category("1", "Электроника"),
                location = Location("1", "Москва"),
                createdAt = "2025-11-10 15:34",
                updatedAt = "2025-11-10 15:34"
            )
        )
        adsAdapter.submitList(mockAds)
    }

    private fun toggleFavorite(ad: Ad) {

    }

    private fun navigateToAdDetails(ad: Ad) {
        findNavController().navigate(R.id.action_nav_home_to_adDetailsFragment)
    }

    private fun showCategoriesDialog() {
        findNavController().navigate(R.id.action_nav_home_to_categoriesDialog)
    }
}