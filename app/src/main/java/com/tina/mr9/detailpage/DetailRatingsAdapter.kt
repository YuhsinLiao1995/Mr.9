package com.tina.mr9.detailpage

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.tina.mr9.data.Ratings
import com.tina.mr9.databinding.ItemDetailRatingBinding

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class DetailRatingsAdapter(
    private val onClickListener: OnClickListener,
    private val paddingDp: Int
) :
    androidx.recyclerview.widget.ListAdapter<Ratings, RecyclerView.ViewHolder>(
        DiffCallback
    ) {

    class OnClickListener(val clickListener: (ratings: Ratings) -> Unit) {
        fun onClick(ratings: Ratings) = clickListener(ratings)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LayoutViewHolder -> {
                val ratings = getItem(position) as Ratings
                (holder).bind(ratings, onClickListener, paddingDp)

            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Ratings>() {
        override fun areItemsTheSame(oldItem: Ratings, newItem: Ratings): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Ratings, newItem: Ratings): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LayoutViewHolder.from(parent)
    }





    class LayoutViewHolder(private var binding: ItemDetailRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ratings: Ratings, onClickListener: OnClickListener, paddingDp: Int) {
            binding.ratings = ratings
            binding.root.setOnClickListener { onClickListener.onClick(ratings) }

            if (ratings.overall_rating!! > 0f) {
                binding.niceRatingBar.setRating(ratings.overall_rating!!)
            } else {
                binding.niceRatingBar.setRating(0f)
            }


            val taglist = ratings.pairings
            val chipGroup = binding.pairingTag

            if (taglist.isNotEmpty()) {
                binding.pairingIcon.visibility = View.VISIBLE
                binding.blank.visibility = View.VISIBLE
            }

            for (index in taglist.indices) {
                val tagName = taglist[index]
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

            if (ratings.comment != ""){
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
//
//    fun chipFun(taglist: MutableList<String>, chipGroup: ChipGroup,paddingDp: Int) {
//
//        for (index in taglist.indices) {
//            val tagName = taglist[index]
//            val chip = Chip(chipGroup.context)
//
//            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
//            chip.text = tagName
//            chip.textSize = 14f
//            chip.setTextColor(Color.WHITE)
//            val states = arrayOf(intArrayOf(-android.R.attr.state_checked))
//            val chipColors = intArrayOf(Color.parseColor("#3f3a3a"))
//            val chipColorsStateList = ColorStateList(states, chipColors)
//            chip.chipBackgroundColor = chipColorsStateList
//            chip.closeIconTint = ColorStateList(states, intArrayOf(Color.WHITE))
//
//            chipGroup.addView(chip)
//        }
//    }


}