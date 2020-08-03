package com.tina.mr9.profile.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Bar
import com.tina.mr9.databinding.ItemBarlikedBinding
import com.tina.mr9.databinding.ItemLikedBinding

//
class BarLikedAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<Bar, RecyclerView.ViewHolder>(
        DiffCallback ) {

    class OnClickListener(val clickListener: (bar:Bar) -> Unit) {
        fun onClick(bar:Bar) = clickListener(bar)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LayoutViewHolder -> {
                val bar = getItem(position) as Bar
                (holder).bind(bar,onClickListener)

            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Bar>() {
        override fun areItemsTheSame(oldItem: Bar, newItem: Bar): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Bar, newItem: Bar): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LayoutViewHolder.from(parent)
    }





    class LayoutViewHolder(private var binding: ItemBarlikedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bar:Bar, onClickListener: OnClickListener) {
            binding.bar = bar

            if (bar.overallRating!! > 0f) {
                binding.niceRatingBar.setRating(bar.overallRating!!)
            } else {
                binding.niceRatingBar.setRating(0f)
            }
            binding.root.setOnClickListener { onClickListener.onClick(bar) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LayoutViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBarlikedBinding
                    .inflate(layoutInflater, parent, false)
                return LayoutViewHolder(binding)
            }

        }



    }

}