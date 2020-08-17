package com.tina.mr9.success

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.databinding.ItemImageBinding

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 */
class SuccessAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<String, RecyclerView.ViewHolder>(
        DiffCallback ) {

    class OnClickListener(val clickListener: (image: String) -> Unit) {
        fun onClick(image: String) = clickListener(image)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LayoutViewHolder -> {
                val image = getItem(position) as String
                (holder).bind(image,onClickListener)

            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LayoutViewHolder.from(parent)
    }





    class LayoutViewHolder(private var binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind( image: String,onClickListener: OnClickListener) {
            binding.image = image

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LayoutViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemImageBinding
                    .inflate(layoutInflater, parent, false)
                return LayoutViewHolder(binding)
            }

        }



    }




}