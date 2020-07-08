package com.tina.mr9

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.HomeItem
import com.tina.mr9.detailpage.DetailImagesAdapter
import com.tina.mr9.home.HomeAdapter

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
//@BindingAdapter("images")
//fun bindRecyclerViewWithImages(recyclerView: RecyclerView, images: List<String>?) {
//    images?.let {
//        recyclerView.adapter?.apply {
//            when (this) {
//                is HomeAdapter -> {
//                    submitImages(it)
//                }
//            }
//        }
//    }
//}
@BindingAdapter("drinks")
fun bindRecyclerViewWithHomeItems(recyclerView: RecyclerView, drinks: List<Drinks>?) {
    drinks?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HomeAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("images")
fun bindRecyclerViewWithImages(recyclerView: RecyclerView, images: List<String>?) {
    images?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is DetailImagesAdapter -> {
                    submitImages(it)
                }
            }
        }
    }
}


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String) {
    imgUrl.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_starred)
                    .error(R.drawable.ic_starred))
            .into(imgView)
    }
}
