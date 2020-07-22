package com.tina.mr9

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tina.mr9.bardetailpage.BarDetailDrinksAdapter
import com.tina.mr9.bardetailpage.BarDetailImagesAdapter
import com.tina.mr9.data.*
import com.tina.mr9.detailpage.DetailImagesAdapter
import com.tina.mr9.detailpage.DetailRatingsAdapter
import com.tina.mr9.friends.FriendsAdapter
import com.tina.mr9.home.HomeAdapter
import com.tina.mr9.list.ListAdapter
import com.tina.mr9.profile.item.LikedAdapter
import com.tina.mr9.profile.item.MyRatingAdapter
import com.tina.mr9.search.item.SearchItemAdapter

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */

@BindingAdapter("drinks")
fun bindRecyclerViewWithHomeItems(recyclerView: RecyclerView, drinks: List<Drinks>?) {
    drinks?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HomeAdapter -> submitList(it)

                is LikedAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("user")
fun bindRecyclerViewWithUser(recyclerView: RecyclerView, user: List<User>?) {
    user?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is FriendsAdapter -> submitList(it)

            }
        }
    }
}

@BindingAdapter("drinkList")
fun bindRecyclerViewWithType(recyclerView: RecyclerView, drinks: List<Drinks>?) {
    drinks?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is ListAdapter -> submitList(it)

//                is BarDetailDrinksAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("rating")
fun bindRecyclerViewWithRating(recyclerView: RecyclerView, ratings: List<Ratings>?) {
    ratings?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is MyRatingAdapter -> submitList(it)

//                is BarDetailDrinksAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("bardrinkList")
fun bindRecyclerViewWithDrinks(recyclerView: RecyclerView, drinks: List<Drinks>?) {
    drinks?.let {
        recyclerView.adapter?.apply {
            when (this) {

                is BarDetailDrinksAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("barList")
fun bindRecyclerViewWithBar(recyclerView: RecyclerView, bar: List<Bar>?) {
    bar?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is com.tina.mr9.bar_list.BarListAdapter -> submitList(it)
            }
        }
    }
}


@BindingAdapter("ratings")
fun bindRecyclerViewWithRatings(recyclerView: RecyclerView, ratings: List<Ratings>?) {
    ratings?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is DetailRatingsAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("search")
fun bindRecyclerViewWithSearch(recyclerView: RecyclerView, search: List<Search>?) {
    Log.d("Tina","search = $search")
    search?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is SearchItemAdapter -> {
                    Log.d("Tina","submitList")
                    submitList(it)
                }

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
                is BarDetailImagesAdapter -> {
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

@BindingAdapter("imageUrl_crop2")
fun bindImage2(imgView: ImageView, imgUrl: String) {
    imgUrl.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .transform(MultiTransformation(CenterCrop(), RoundedCorners(50)))
                    .placeholder(R.drawable.ic_starred)
                    .error(R.drawable.ic_starred))
            .into(imgView)
    }
}

@BindingAdapter("imageUrl_crop")
fun bindImage1(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.ic_nav_profile)
                    .error(R.drawable.ic_nav_profile))
            .into(imgView)
    }
}



@BindingAdapter("itemPosition", "itemCount")
fun setupPaddingForGridItems(layout: ConstraintLayout, position: Int, count: Int) {

    val outsideHorizontal = Mr9Application.instance.resources.getDimensionPixelSize(R.dimen.space_outside_horizontal_catalog_item)
    val insideHorizontal = Mr9Application.instance.resources.getDimensionPixelSize(R.dimen.space_inside_horizontal_catalog_item)
    val outsideVertical = Mr9Application.instance.resources.getDimensionPixelSize(R.dimen.space_outside_vertical_catalog_item)
    val insideVertical = Mr9Application.instance.resources.getDimensionPixelSize(R.dimen.space_inside_vertical_catalog_item)

    val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

    when {
        position == 0 -> { // first item and confirm whether only 1 row
            layoutParams.setMargins(outsideHorizontal, outsideVertical, insideHorizontal, if (count > 2) insideVertical else outsideVertical)
        }
        position == 1 -> { // second item and confirm whether only 1 row
            layoutParams.setMargins(insideHorizontal, outsideVertical, outsideHorizontal, if (count > 2) insideVertical else outsideVertical)
        }
        count % 2 == 0 && position == count - 1 -> { // count more than 2 and item count is even
            layoutParams.setMargins(insideHorizontal, insideVertical, outsideHorizontal, outsideVertical)
        }
        (count % 2 == 1 && position == count - 1) || (count % 2 == 0 && position == count - 2) -> {
            layoutParams.setMargins(outsideHorizontal, insideVertical, insideHorizontal, outsideVertical)
        }
        position % 2 == 0 -> { // even
            when (position) {
                count - 1 -> layoutParams.setMargins(insideHorizontal, insideVertical, outsideHorizontal, outsideVertical) // last 1
                count - 2 -> layoutParams.setMargins(outsideHorizontal, insideVertical, insideHorizontal, outsideVertical) // last 2
                else -> layoutParams.setMargins(outsideHorizontal, insideVertical, insideHorizontal, insideVertical)
            }
        }
        position % 2 == 1 -> { // odd
            when (position) {
                count - 1 -> layoutParams.setMargins(outsideHorizontal, insideVertical, insideHorizontal, outsideVertical) // last 1
                else -> layoutParams.setMargins(insideHorizontal, insideVertical, outsideHorizontal, insideVertical)
            }
        }
    }

    layout.layoutParams = layoutParams
}
