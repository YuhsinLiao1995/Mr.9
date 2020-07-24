package com.tina.mr9.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Ratings
import com.tina.mr9.databinding.ItemFriendsRatingBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger

class FriendsRatingAdapter(private val onClickListener: OnClickListener, private val status: Boolean) :
    androidx.recyclerview.widget.ListAdapter<Ratings, RecyclerView.ViewHolder>(
        DiffCallback ) {

    class OnClickListener(val clickListener: (ratings: Ratings) -> Unit) {
        fun onClick(ratings: Ratings) = clickListener(ratings)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LayoutViewHolder -> {
                val ratings = getItem(position) as Ratings
                (holder).bind(ratings,onClickListener,status)

            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Ratings>() {
        override fun areItemsTheSame(oldItem: Ratings, newItem: Ratings): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Ratings, newItem: Ratings): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LayoutViewHolder.from(parent)
    }






    class LayoutViewHolder(private var binding: ItemFriendsRatingBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(ratings: Ratings, onClickListener: OnClickListener, status: Boolean) {
            binding.rating = ratings

            if (ratings.overall_rating != -1f) {
                binding.niceRatingBar.setRating(ratings.overall_rating)
            }
            Logger.d("ratings.overall_rating = ${ratings.overall_rating}")
            binding.root.setOnClickListener { onClickListener.onClick(ratings) }

//            if (status){
//                binding.content.visibility = View.VISIBLE
//                Logger.d("View.VISIBLE")
//            }else{
//                binding.content.visibility = View.GONE
//                Logger.d("View.GONE")
//            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LayoutViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFriendsRatingBinding
                    .inflate(layoutInflater, parent, false)
                return LayoutViewHolder(binding)
            }

        }



    }

}