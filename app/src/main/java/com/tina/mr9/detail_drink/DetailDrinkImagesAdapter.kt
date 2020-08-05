package com.tina.mr9.detail_drink

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.databinding.ItemDetailsImagesBinding

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class DetailDrinkImagesAdapter : RecyclerView.Adapter<DetailDrinkImagesAdapter.ImageViewHolder>() {

    private lateinit var context: Context
    private var images: List<String>? = null

    class ImageViewHolder(private var binding: ItemDetailsImagesBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {

            imageUrl.let {

                binding.imageUrl = it
                binding.executePendingBindings()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        context = parent.context
        return ImageViewHolder(ItemDetailsImagesBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        images?.let {
            holder.bind(it[getRealPosition(position)])
        }
    }

    override fun getItemCount(): Int {
        return images?.let { Int.MAX_VALUE } ?: 0
    }

    private fun getRealPosition(position: Int): Int = images?.let {
        position % it.size
    } ?: 0

    fun submitImages(images: List<String>) {
        this.images = images
        notifyDataSetChanged()
    }
    
}
