package com.tina.mr9.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Drink
import com.tina.mr9.databinding.ItemSearchedDrinkBinding

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 */
class SearchedDrinksAdapter(private val onClickListener: OnClickListener) :
        ListAdapter<Drink, SearchedDrinksAdapter.DrinkViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (drink: Drink) -> Unit) {
        fun onClick(drink: Drink) = clickListener(drink)
    }



    class DrinkViewHolder(private var binding: ItemSearchedDrinkBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(drink: Drink, onClickListener: OnClickListener) {

            binding.drink = drink
            binding.root.setOnClickListener { onClickListener.onClick(drink) }
            binding.executePendingBindings()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(ItemSearchedDrinkBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {

                holder.bind((getItem(position) as Drink), onClickListener)

        }
    }


