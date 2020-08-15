package com.tina.mr9.profile.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.component.OutlineProvider
import com.tina.mr9.component.OutlineProvider4Drink
import com.tina.mr9.data.Drink
import com.tina.mr9.databinding.ItemLikedDrinkBinding

class LikedDrinkAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<Drink, RecyclerView.ViewHolder>(
        DiffCallback ) {

    class OnClickListener(val clickListener: (drink:Drink) -> Unit) {
        fun onClick(drink:Drink) = clickListener(drink)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LayoutViewHolder -> {
                val drinks = getItem(position) as Drink
                (holder).bind(drinks,onClickListener)

            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LayoutViewHolder.from(parent)
    }





    class LayoutViewHolder(private var binding: ItemLikedDrinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(drink:Drink, onClickListener: OnClickListener) {
            binding.drink = drink

            binding.outlineProvider = OutlineProvider4Drink()

            if (drink.overall_rating > 0f) {
                binding.niceRatingBar.setRating(drink.overall_rating)
            } else {
                binding.niceRatingBar.setRating(0f)
            }
            binding.root.setOnClickListener { onClickListener.onClick(drink) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LayoutViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLikedDrinkBinding
                    .inflate(layoutInflater, parent, false)
                return LayoutViewHolder(binding)
            }

        }



    }

}