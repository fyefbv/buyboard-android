package com.example.buyboard_android.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buyboard_android.R
import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.databinding.ItemAdBinding

class AdsAdapter(
    private val onAdClick: (Ad) -> Unit,
    private val onFavoriteClick: (Ad) -> Unit
) : ListAdapter<Ad, AdsAdapter.AdViewHolder>(AdDiffCallback()) {

    inner class AdViewHolder(private val binding: ItemAdBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ad: Ad) {
            binding.adTitle.text = ad.title
            binding.adPrice.text = "${ad.price.toInt()} ₽"
            binding.adLocation.text = ad.location.name
            binding.adDate.text = ad.createdAt
            binding.adCategory.text = ad.category.name
            binding.adImage.setImageResource(R.drawable.ic_launcher_background)

            updateFavoriteButtonAppearance(ad.isFavorite)

            binding.favoriteButton.setOnClickListener {
                onFavoriteClick(ad)
            }

            binding.root.setOnClickListener {
                onAdClick(ad)
            }
        }

        private fun updateFavoriteButtonAppearance(isFavorite: Boolean) {
            if (isFavorite) {
                binding.favoriteButton.setImageResource(R.drawable.ic_like)
                binding.favoriteButton.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.red)
                )
            } else {
                binding.favoriteButton.setImageResource(R.drawable.ic_like)
                binding.favoriteButton.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.gray)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val itemAdBinding = ItemAdBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AdViewHolder(itemAdBinding)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AdDiffCallback : DiffUtil.ItemCallback<Ad>() {
        override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem == newItem
        }
    }
}