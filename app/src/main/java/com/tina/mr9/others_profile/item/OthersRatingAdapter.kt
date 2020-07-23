package com.tina.mr9.others_profile.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Ratings
import com.tina.mr9.databinding.ItemMyRatingBinding
import com.tina.mr9.databinding.ItemOthersRatingBinding
import com.tina.mr9.util.Logger


class OthersRatingAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<Ratings, RecyclerView.ViewHolder>(
        DiffCallback ) {

    class OnClickListener(val clickListener: (ratings: Ratings) -> Unit) {
        fun onClick(ratings: Ratings) = clickListener(ratings)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LayoutViewHolder -> {
                val ratings = getItem(position) as Ratings
                (holder).bind(ratings,onClickListener)

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





    class LayoutViewHolder(private var binding: ItemOthersRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ratings: Ratings, onClickListener: OnClickListener) {
            binding.rating = ratings
            if (ratings.overall_rating != -1f) {
                binding.niceRatingBar.setRating(ratings.overall_rating)
            }
            Logger.d("ratings.overall_rating = ${ratings.overall_rating}")
            binding.root.setOnClickListener { onClickListener.onClick(ratings) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LayoutViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemOthersRatingBinding
                    .inflate(layoutInflater, parent, false)
                return LayoutViewHolder(binding)
            }

        }



    }

}