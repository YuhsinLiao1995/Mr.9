package com.tina.mr9.rate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.HomeItem
import com.tina.mr9.data.Product
import com.tina.mr9.databinding.ItemSearchedDrinkBinding

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * [HomeItem], including computing diffs between lists.
 * @param onClickListener a lambda that takes the
 */
class RateSearchedDrinksAdapter(private val onClickListener: OnClickListener) :
        ListAdapter<Drinks, RateSearchedDrinksAdapter.FullProductViewHolder>(DiffCallback) {
    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Product]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Product]
     */
    class OnClickListener(val clickListener: (Drinks) -> Unit){
        fun onClick(drinks: Drinks) = clickListener(drinks)
    }



    class FullProductViewHolder(private var binding: ItemSearchedDrinkBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(drinks: Drinks, onClickListener: OnClickListener) {

            binding.drink = drinks
            binding.root.setOnClickListener { onClickListener.onClick(drinks) }
            binding.executePendingBindings()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullProductViewHolder {
        return FullProductViewHolder(ItemSearchedDrinkBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: FullProductViewHolder, position: Int) {

                holder.bind((getItem(position) as Drinks), onClickListener)

        }
    }


