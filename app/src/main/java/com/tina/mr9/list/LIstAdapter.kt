package com.tina.mr9.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.HomeItem
import com.tina.mr9.databinding.ItemHomeFullBinding
import com.tina.mr9.databinding.ItemListBinding


/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * [HomeItem], including computing diffs between lists.
 * @param onClickListener a lambda that takes the
 */
class ListAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<Drinks, RecyclerView.ViewHolder>(
        DiffCallback ) {

    class OnClickListener(val clickListener: (drinks: Drinks) -> Unit) {
        fun onClick(drinks: Drinks) = clickListener(drinks)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LayoutViewHolder -> {
                val drink = getItem(position) as Drinks
                (holder).bind(drink,onClickListener)

            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Drinks>() {
        override fun areItemsTheSame(oldItem: Drinks, newItem: Drinks): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Drinks, newItem: Drinks): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LayoutViewHolder.from(parent)
    }





    class LayoutViewHolder(private var binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind( drinks: Drinks,onClickListener: OnClickListener) {
            binding.drink = drinks
            binding.root.setOnClickListener { onClickListener.onClick(drinks) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LayoutViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListBinding
                    .inflate(layoutInflater, parent, false)
                return LayoutViewHolder(binding)
            }

        }



    }




}



