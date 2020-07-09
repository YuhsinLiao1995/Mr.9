package com.tina.mr9.search.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Search
import com.tina.mr9.databinding.ItemSearchBinding

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * [Product], including computing diffs between lists.
 * @param onClickListener a lambda that takes the
 */
class PagingAdapter(private val onClickListener: OnClickListener) :
        PagedListAdapter<Search, PagingAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(private var binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(search: Search, itemCount: Int) {
            binding.search = search
            binding.itemPosition = adapterPosition
            binding.itemCount = itemCount
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Product]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val search = getItem(position)
        search?.let {

            holder.itemView.setOnClickListener {
                onClickListener.onClick(search)
            }
            holder.bind(search, itemCount)
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Product]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Product]
     */
    class OnClickListener(val clickListener: (search: Search) -> Unit) {
        fun onClick(search: Search) = clickListener(search)
    }
}
