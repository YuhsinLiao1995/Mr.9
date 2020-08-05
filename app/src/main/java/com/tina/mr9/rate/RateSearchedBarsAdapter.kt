package com.tina.mr9.rate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.*
import com.tina.mr9.databinding.ItemFriendsBinding
import com.tina.mr9.databinding.ItemSearchedBarBinding
import com.tina.mr9.databinding.ItemSearchedDrinkBinding
import com.tina.mr9.databinding.ItemSearchedDrinkBindingImpl

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 */
class RateSearchedBarAdapter(private val onClickListener: OnClickListener) :
        ListAdapter<Bar, RateSearchedBarAdapter.FullProductViewHolder>(DiffCallback) {
    /**
     */
    class OnClickListener(val clickListener: (bars: Bar) -> Unit) {
        fun onClick(bars: Bar) = clickListener(bars)
    }



    class FullProductViewHolder(private var binding: ItemSearchedBarBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bars: Bar, onClickListener: OnClickListener) {

            binding.bar = bars
            binding.root.setOnClickListener { onClickListener.onClick(bars) }
            binding.executePendingBindings()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullProductViewHolder {
        return FullProductViewHolder(ItemSearchedBarBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: FullProductViewHolder, position: Int) {

                holder.bind((getItem(position) as Bar), onClickListener)

        }
    }


