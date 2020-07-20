package com.tina.mr9.profile.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//
//class CatalogAdapter(val onClickListener: OnClickListener) : ListAdapter<Product, RecyclerView.ViewHolder>(DiffCallback) {
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val product = getItem(position) as Product
//        (holder as ProductLayoutViewHolder).bind(product, onClickListener)
//
//    }
//
//    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
//        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return ProductLayoutViewHolder.from(parent)
//    }
//    class OnClickListener(val clickListener: (product: Product) -> Unit) {
//        fun onClick(product: Product) = clickListener(product)
//    }
//
//
//}
//
//class ProductLayoutViewHolder(private var binding: ProductViewItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//    fun bind(
//        product: Product,
//        onClickListener: CatalogAdapter.OnClickListener
//    ) {
//        binding.product = product
//        binding.root.setOnClickListener {
//            onClickListener.onClick(product)
//        }
//        binding.executePendingBindings()
//    }
//
//    companion object {
//        fun from(parent: ViewGroup): ProductLayoutViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ProductViewItemBinding
//                    .inflate(layoutInflater, parent, false)
//            return ProductLayoutViewHolder(binding)
//        }
//
//    }
//
//
//
//}