package com.tina.mr9.rate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.HomeItem
import com.tina.mr9.data.Product
import com.tina.mr9.databinding.ItemHomeFullBinding

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * [HomeItem], including computing diffs between lists.
 * @param onClickListener a lambda that takes the
 */
class RateAdapter(private val onClickListener: OnClickListener) :
        ListAdapter<HomeItem, RecyclerView.ViewHolder>(DiffCallback) {
    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Product]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Product]
     */
    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }



    class FullProductViewHolder(private var binding: ItemHomeFullBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, onClickListener: OnClickListener) {

            binding.product = product
            binding.root.setOnClickListener { onClickListener.onClick(product) }
            binding.executePendingBindings()
        }
    }



    companion object DiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_TITLE            = 0x00
        private const val ITEM_VIEW_TYPE_PRODUCT_FULL     = 0x01
        private const val ITEM_VIEW_TYPE_PRODUCT_COLLAGE  = 0x02
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FullProductViewHolder(ItemHomeFullBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

//                holder.bind((getItem(position) as HomeItem.FullProduct).product, onClickListener)

        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return when (getItem(position)) {
//            is HomeItem.Title -> ITEM_VIEW_TYPE_TITLE
//            is HomeItem.FullProduct -> ITEM_VIEW_TYPE_PRODUCT_FULL
//            is HomeItem.CollageProduct -> ITEM_VIEW_TYPE_PRODUCT_COLLAGE
//        }
//    }
