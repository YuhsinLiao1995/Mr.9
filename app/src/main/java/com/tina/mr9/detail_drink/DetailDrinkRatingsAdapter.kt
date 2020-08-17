package com.tina.mr9.detail_drink

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.tina.mr9.data.Rating
import com.tina.mr9.databinding.ItemDetailRatingBinding

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class DetailDrinkRatingsAdapter(
    private val onClickListener: OnClickListener,
    private val paddingDp: Int
) :
    androidx.recyclerview.widget.ListAdapter<Rating, RecyclerView.ViewHolder>(
        DiffCallback
    ) {

    class OnClickListener(val clickListener: (rating: Rating) -> Unit) {
        fun onClick(rating: Rating) = clickListener(rating)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LayoutViewHolder -> {
                val ratings = getItem(position) as Rating
                (holder).bind(ratings, onClickListener, paddingDp)

            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Rating>() {
        override fun areItemsTheSame(oldItem: Rating, newItem: Rating): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Rating, newItem: Rating): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LayoutViewHolder.from(parent)
    }

    class LayoutViewHolder(private var binding: ItemDetailRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rating: Rating, onClickListener: OnClickListener, paddingDp: Int) {
            binding.ratings = rating
            binding.root.setOnClickListener { onClickListener.onClick(rating) }

            if (rating.overall_rating > 0f) {
                binding.niceRatingBar.setRating(rating.overall_rating)
            } else {
                binding.niceRatingBar.setRating(0f)
            }


            val tagList = rating.pairings
            val chipGroup = binding.pairingTag

            if (tagList.isNotEmpty()) {
                binding.pairingIcon.visibility = View.VISIBLE
                binding.blank.visibility = View.VISIBLE
            }

            for (index in tagList.indices) {
                val tagName = tagList[index]
                val chip = Chip(chipGroup.context)

                chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
                chip.text = tagName
                chip.textSize = 14f
                chip.setTextColor(Color.WHITE)
                val states = arrayOf(intArrayOf(-android.R.attr.state_checked))
                val chipColors = intArrayOf(Color.parseColor("#999999"))
                val chipColorsStateList = ColorStateList(states, chipColors)
                chip.chipBackgroundColor = chipColorsStateList
                chip.closeIconTint = ColorStateList(states, intArrayOf(Color.WHITE))

                chipGroup.addView(chip)
            }

            if (rating.comment != ""){
                binding.blank2.visibility = View.VISIBLE
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LayoutViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDetailRatingBinding
                    .inflate(layoutInflater, parent, false)
                return LayoutViewHolder(binding)
            }
        }
    }
}