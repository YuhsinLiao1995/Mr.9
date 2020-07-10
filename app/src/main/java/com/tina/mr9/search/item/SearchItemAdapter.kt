package com.tina.mr9.search.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Search
import com.tina.mr9.databinding.ItemSearchBinding

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * [Product], including computing diffs between lists.
 * @param onClickListener a lambda that takes the
 */
class SearchItemAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<Search, RecyclerView.ViewHolder>(
        DiffCallback ) {

    class OnClickListener(val clickListener: (search: Search) -> Unit) {
        fun onClick(search: Search) = clickListener(search)
    }



    companion object DiffCallback : DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Search, newItem:Search): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LayoutViewHolder.from(parent)
    }







    class LayoutViewHolder(private var binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind( search: Search,onClickListener: OnClickListener, itemCount: Int) {
            binding.search = search
            binding.root.setOnClickListener { onClickListener.onClick(search) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LayoutViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSearchBinding
                    .inflate(layoutInflater, parent, false)
                return LayoutViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val search = getItem(position)
        search?.let {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(search)
            }
            (holder as LayoutViewHolder).bind(search,onClickListener, itemCount)
        }
    }


}

