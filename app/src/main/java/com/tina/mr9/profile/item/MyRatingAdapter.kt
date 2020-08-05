package com.tina.mr9.profile.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Rating
import com.tina.mr9.databinding.ItemMyRatingBinding
import com.tina.mr9.util.Logger


class MyRatingAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<Rating, RecyclerView.ViewHolder>(
        DiffCallback ) {

    class OnClickListener(val clickListener: (rating: Rating) -> Unit) {
        fun onClick(rating: Rating) = clickListener(rating)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LayoutViewHolder -> {
                val ratings = getItem(position) as Rating
                (holder).bind(ratings,onClickListener)

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





    class LayoutViewHolder(private var binding: ItemMyRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rating: Rating, onClickListener: OnClickListener) {
            binding.rating = rating
            if (rating.overall_rating != -1f) {
                binding.niceRatingBar.setRating(rating.overall_rating)
            }
            Logger.d("ratings.overall_rating = ${rating.overall_rating}")
            binding.root.setOnClickListener { onClickListener.onClick(rating) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LayoutViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMyRatingBinding
                    .inflate(layoutInflater, parent, false)
                return LayoutViewHolder(binding)
            }

        }



    }

}