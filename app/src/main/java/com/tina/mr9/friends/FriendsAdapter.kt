package com.tina.mr9.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.User
import com.tina.mr9.databinding.ItemFriendsBinding

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 */
class FriendsAdapter(private val onClickListener: OnClickListener) :
        ListAdapter<User, FriendsAdapter.FullProductViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (searchUser: User) -> Unit) {
        fun onClick(searchUser: User) = clickListener(searchUser)
    }



    class FullProductViewHolder(private var binding: ItemFriendsBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchUser: User, onClickListener: OnClickListener) {

            binding.user = searchUser
            binding.root.setOnClickListener { onClickListener.onClick(searchUser) }
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


