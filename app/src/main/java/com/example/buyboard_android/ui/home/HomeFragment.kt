package com.example.buyboard_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buyboard_android.R
import com.example.buyboard_android.data.models.Ad
import com.example.buyboard_android.databinding.FragmentHomeBinding
import com.example.buyboard_android.ui.home.adapters.AdsAdapter


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adsAdapter: AdsAdapter

    private val mockAds = listOf(
        Ad(
            id = "1",
            title = "Смартфон Samsung Galaxy S21",
            description = "Отличное состояние",
            price = 25000.0,
            category = "Электроника",
            location = "Москва",
            date = "2025-11-10 15:34",
            sellerId = "user1",
            sellerName = "Иван Иванов"
        ),
        Ad(
            id = "2",
            title = "Диван угловой",
            description = "Новый диван",
            price = 15000.0,
            category = "Мебель",
            location = "Тверь",
            date = "2025-11-10 15:34",
            sellerId = "user2",
            sellerName = "Петр Петров"
        ),
        Ad(
            id = "3",
            title = "Футболка мужская",
            description = "Новая с биркой",
            price = 1500.0,
            category = "Одежда",
            location = "Казань",
            date = "2025-11-10 15:34",
            sellerId = "user3",
            sellerName = "Алексей Сидоров"
        )
    )

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
                    navigateToAdDetails(ad.id)
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
            showCategoriesFragment()
        }
    }

    private fun loadAds() {
        adsAdapter.submitList(mockAds)
    }

    private fun showCategoriesFragment() {
        val categoriesFragment = CategoriesFragment()
        categoriesFragment.show(parentFragmentManager, "categories_dialog")
    }

    private fun toggleFavorite(ad: Ad) {
        val newFavoriteState = !ad.isFavorite

        val updatedAds = adsAdapter.currentList.map { currentAd ->
            if (currentAd.id == ad.id) {
                currentAd.copy(isFavorite = newFavoriteState)
            } else {
                currentAd
            }
        }
        adsAdapter.submitList(updatedAds)

        val message = if (newFavoriteState) "Добавлено в избранное" else "Удалено из избранного"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToAdDetails(adId: String) {
        Toast.makeText(requireContext(), "Переход к объявлению $adId", Toast.LENGTH_SHORT).show()
    }
}