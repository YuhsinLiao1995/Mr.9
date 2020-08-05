package com.tina.mr9.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
@Parcelize
data class Drink(
    var id: String = "",
    var address: String = "",
    var name: String = "",
    var bar: String = "",
    var price: Int = 0,
    var base: List<String> = emptyList(),
    var contents: List<String> = emptyList(),
    var pairings: List<String> = emptyList(),
    var category: String = "",
    var main_image: String = "",
    var images: List<String> = emptyList(),
    var tag: List<String> = emptyList(),
    var rating: Rating = Rating(),
    var likedBy: List<String> = emptyList(),
    var amtRating: Int = -1,
    var body: Float = -1f,
    var sweet: Float = -1f,
    var sour: Float = -1f,
    var take_again: Boolean = false,
    var overall_rating: Float = -1f,
    var createdTime: Long = -1

) : Parcelable {

}



