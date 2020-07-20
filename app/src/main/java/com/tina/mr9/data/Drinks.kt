package com.tina.mr9.data

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
@Parcelize
data class Drinks(
    var id: String = "",
    var address: String = "",
    var name: String = "",
    var bar: String = "",
    var base: List<String> = emptyList(),
    var contents: List<String> = emptyList(),
    var pairings: List<String> = emptyList(),
    var category: String = "",
    var main_image: String = "",
    var images: List<String> = emptyList(),
    var tag: List<String> = emptyList(),
    var rating: Ratings = Ratings(),
    var amtRating: Int = -1,
    var strong: Long = -1,
    var sweet: Float = -1f,
    var sour: Float = -1f,
    var take_again: Boolean = false,
    var overall_rating: Float = -1f,
    var createdTime: Long = -1

) : Parcelable {
//    lateinit var value: MutableList<Drinks>
    @IgnoredOnParcel
    var contentsText: String = "contents : $bar"

}

@Parcelize
data class Ratings(
    var id: String = "",
    var createdTime: Long = -1,
    var acidic: Float = -1f,
    var alcohol_ABV: Int = -1,
    var author: String = "",
    var authorName: String = "",
    var authorImage:String = "",
    var address:String = "",
    var main_photo: String = "",
    var base: List<String> = emptyList(),
    var pairings: List<String> = emptyList(),
    var category: String = "",
    var comment: String = "",
    var contents: List<String> = emptyList(),
    var created_time: Long = -1,
    var images: List<String>? = emptyList(),
    var overall_rating: Float = -1f,
    var strong: Long = -1,
    var sweet: Float = -1f,
    var sour: Float = -1f,
    var take_again: Boolean = false,
    var bar: String = "",
    var name: String = ""
) : Parcelable{
}



