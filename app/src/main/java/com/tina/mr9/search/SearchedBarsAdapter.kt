package com.tina.mr9.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.*
import com.tina.mr9.databinding.ItemSearchedBarBinding

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 */
class SearchedBarAdapter(private val onClickListener: OnClickListener) :
        ListAdapter<Bar, SearchedBarAdapter.BarViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (bars: Bar) -> Unit) {
        fun onClick(bars: Bar) = clickListener(bars)
    }



    class BarViewHolder(private var binding: ItemSearchedBarBinding):
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarViewHolder {
        return BarViewHolder(ItemSearchedBarBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: BarViewHolder, position: Int) {

                holder.bind((getItem(position) as Bar), onClickListener)

        }
    }


