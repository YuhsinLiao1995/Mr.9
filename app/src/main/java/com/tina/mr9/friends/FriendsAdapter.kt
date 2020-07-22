package com.tina.mr9.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.HomeItem
import com.tina.mr9.data.Product
import com.tina.mr9.data.User
import com.tina.mr9.databinding.ItemFriendsBinding

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * [HomeItem], including computing diffs between lists.
 * @param onClickListener a lambda that takes the
 */
class FriendsAdapter(private val onClickListener: OnClickListener) :
        ListAdapter<User, FriendsAdapter.FullProductViewHolder>(DiffCallback) {
    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Product]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Product]
     */
    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }



    class FullProductViewHolder(private var binding: ItemFriendsBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onClickListener: OnClickListener) {

            binding.user = user
            binding.root.setOnClickListener { onClickListener.onClick(user) }
            binding.executePendingBindings()
        }
    }



    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        private const val ITEM_VIEW_TYPE_TITLE            = 0x00
        private const val ITEM_VIEW_TYPE_PRODUCT_FULL     = 0x01
        private const val ITEM_VIEW_TYPE_PRODUCT_COLLAGE  = 0x02
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullProductViewHolder {
        return FullProductViewHolder(ItemFriendsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: FullProductViewHolder, position: Int) {

                holder.bind((getItem(position) as User), onClickListener)

        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return when (getItem(position)) {
//            is HomeItem.Title -> ITEM_VIEW_TYPE_TITLE
//            is HomeItem.FullProduct -> ITEM_VIEW_TYPE_PRODUCT_FULL
//            is HomeItem.CollageProduct -> ITEM_VIEW_TYPE_PRODUCT_COLLAGE
//        }
//    }
