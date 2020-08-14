package com.tina.mr9

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tina.mr9.data.*
import com.tina.mr9.detail_bar.DetailBarDrinksAdapter
import com.tina.mr9.detail_bar.DetailBarImagesAdapter
import com.tina.mr9.detail_drink.DetailDrinkImagesAdapter
import com.tina.mr9.detail_drink.DetailDrinkRatingsAdapter
import com.tina.mr9.ext.toDisplayFormat
import com.tina.mr9.friends.FriendsAdapter
import com.tina.mr9.friends.FriendsRatingAdapter
import com.tina.mr9.home.HomeAdapter
import com.tina.mr9.list_drink.ListAdapter
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.others_profile.item.OthersRatingAdapter
import com.tina.mr9.profile.item.LikedBarAdapter
import com.tina.mr9.profile.item.LikedDrinkAdapter
import com.tina.mr9.profile.item.MyRatingAdapter
import com.tina.mr9.rate.RateSearchedBarAdapter
import com.tina.mr9.rate.RateSearchedDrinksAdapter
import com.tina.mr9.search.SearchedBarAdapter
import com.tina.mr9.search.SearchedDrinksAdapter
import com.tina.mr9.search.item.SearchItemAdapter
import okhttp3.internal.trimSubstring
import java.math.BigDecimal
import kotlin.time.ExperimentalTime

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */

@BindingAdapter("drinks")
fun bindRecyclerViewWithHomeItems(recyclerView: RecyclerView, drinks: List<Drink>?) {
    drinks?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HomeAdapter -> submitList(it)

                is LikedDrinkAdapter -> submitList(it)
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
fun bindRecyclerViewWithType(recyclerView: RecyclerView, drinks: List<Drink>?) {
    drinks?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is ListAdapter -> submitList(it)

                is SearchedDrinksAdapter -> submitList(it)

                is RateSearchedDrinksAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("rating")
fun bindRecyclerViewWithRating(recyclerView: RecyclerView, ratings: List<Rating>?) {
    ratings?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is MyRatingAdapter -> submitList(it)

                is OthersRatingAdapter -> submitList(it)

                is FriendsRatingAdapter -> submitList(it)

            }
        }
    }
}

@BindingAdapter("bardrinkList")
fun bindRecyclerViewWithDrinks(recyclerView: RecyclerView, drinks: List<Drink>?) {
    drinks?.let {
        recyclerView.adapter?.apply {
            when (this) {

                is DetailBarDrinksAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("barList")
fun bindRecyclerViewWithBar(recyclerView: RecyclerView, bar: List<Bar>?) {
    bar?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is com.tina.mr9.list_bar.ListBarAdapter -> submitList(it)

                is SearchedBarAdapter -> submitList(it)

                is RateSearchedBarAdapter -> submitList(it)

                is LikedBarAdapter -> submitList(it)
            }
        }
    }
}

@ExperimentalTime
@BindingAdapter("timeToDisplayFormat")
fun bindDisplayFormatTime(textView: TextView, time: Long?) {
    textView.text = time?.toDisplayFormat()?.trimSubstring(0,10)

}


@BindingAdapter("ratings")
fun bindRecyclerViewWithRatings(recyclerView: RecyclerView, ratings: List<Rating>?) {
    ratings?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is DetailDrinkRatingsAdapter -> submitList(it)
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
                is DetailDrinkImagesAdapter -> {
                    submitImages(it)
                }
                is DetailBarImagesAdapter -> {
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
                    .placeholder(R.drawable.background_placeholder)
                    .error(R.drawable.background_placeholder))
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
                    .transform(MultiTransformation(CenterCrop(),
                        RoundedCorners(20)))
                    .placeholder(R.drawable.background_placeholder)
                    .error(R.drawable.background_placeholder))
            .into(imgView)
    }
}

@BindingAdapter("imageUrl_crop3")
fun bindImage3(imgView: ImageView, imgUrl: String) {
    imgUrl.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .transform(MultiTransformation(CenterCrop(), RoundedCorners(25)))
                    .placeholder(R.drawable.background_placeholder)
                    .error(R.drawable.background_placeholder))
            .into(imgView)
    }
}

@BindingAdapter("imageUrl_crop4")
fun bindImage4(imgView: ImageView, imgUrl: String) {
    imgUrl.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .transform(MultiTransformation(CenterCrop(), RoundedCorners(35)))
                    .placeholder(R.drawable.background_placeholder)
                    .error(R.drawable.background_placeholder)
            )
            .into(imgView)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("arrayToString")
fun bindArrayToString(textView: TextView, args: List<String>?) {
    textView.text = (args.let { java.lang.String.join(", ", args ?: listOf()) })
}

@SuppressLint("SetTextI18n")
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("round")
fun bindFloatToString(textView: TextView, args: Float) {

    if (args >= 0) {
        textView.text =
            args.toDouble().toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString()
    }else{
        textView.text = "NA"
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

/**
 * According to [LoadApiStatus] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiStatus")
fun bindApiStatus(view: ProgressBar, status: LoadApiStatus?) {
    when (status) {
        LoadApiStatus.LOADING -> view.visibility = View.VISIBLE
        LoadApiStatus.DONE, LoadApiStatus.ERROR -> view.visibility = View.GONE
    }
}

/**
 * According to [message] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiErrorMessage")
fun bindApiErrorMessage(view: TextView, message: String?) {
    when (message) {
        null, "" -> {
            view.visibility = View.GONE
        }
        else -> {
            view.text = message
            view.visibility = View.VISIBLE
        }
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
