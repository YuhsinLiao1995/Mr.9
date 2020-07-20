package com.tina.mr9.detailpage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.data.Drinks
import com.tina.mr9.databinding.ItemDetailsImagesBinding
import com.tina.mr9.list.ListAdapter

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class DetailImagesAdapter : RecyclerView.Adapter<DetailImagesAdapter.ImageViewHolder>() {

    private lateinit var context: Context
    // the data of adapter
    private var images: List<String>? = null

    class ImageViewHolder(private var binding: ItemDetailsImagesBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, imageUrl: String) {

            imageUrl.let {
                binding.imageUrl = it

                // Make sure the size of each image item can display correct
//                val displayMetrics = DisplayMetrics()
//                (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
//                binding.imageDrinks.layoutParams = ConstraintLayout.LayoutParams(displayMetrics.widthPixels,
//                    context.resources.getDimensionPixelSize(R.dimen.height_detail_gallery))

                binding.executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        context = parent.context
        return ImageViewHolder(ItemDetailsImagesBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        images?.let {
            holder.bind(context, it[getRealPosition(position)])
        }
//        when (holder) {
//            is ImageViewHolder -> {
//                val images = getItem(position) as String
//                (holder).bind(context,images)
//
//            }
//        }
    }

    override fun getItemCount(): Int {
        return images?.let { Int.MAX_VALUE } ?: 0
    }

    private fun getRealPosition(position: Int): Int = images?.let {
        position % it.size
    } ?: 0

    /**
     * Submit data list and refresh adapter by [notifyDataSetChanged]
     * @param images: [List] [String]
     */
    fun submitImages(images: List<String>) {
        this.images = images
        notifyDataSetChanged()
    }
    
}
