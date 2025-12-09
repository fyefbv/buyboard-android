package com.example.buyboard_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.buyboard_android.R
import com.example.buyboard_android.data.models.Category
import com.example.buyboard_android.databinding.FragmentCategoriesBinding
import com.example.buyboard_android.ui.home.adapters.CategoriesAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class CategoriesFragment : DialogFragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter

    private val mockCategories = listOf(
        Category("all", "Все категории"),
        Category("electronics", "Электроника"),
        Category("clothing", "Одежда"),
        Category("furniture", "Мебель"),
        Category("auto", "Авто"),
        Category("real_estate", "Недвижимость"),
        Category("books", "Книги"),
        Category("sports", "Спорт"),
        Category("children", "Детские товары"),
        Category("animals", "Животные")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        loadCategories()
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

    private fun setupRecyclerView() {
        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
        }

        binding.categoriesRecyclerView.apply {
            this.layoutManager = layoutManager
            categoriesAdapter = CategoriesAdapter(
                onCategoryClick = { category ->

                }
            )
            adapter = categoriesAdapter
        }
    }

    private fun setupClickListeners() {
        binding.closeButton.setOnClickListener {
            dismissDialog()
        }

        binding.clearButton.setOnClickListener {
            categoriesAdapter.clearSelection()
        }

        binding.applyButton.setOnClickListener {
            dismissDialog()
        }
    }

    private fun loadCategories() {
        categoriesAdapter.submitList(mockCategories.toMutableList())
    }

    private fun dismissDialog() {
        findNavController().navigateUp()
    }
}