package com.tina.mr9.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(
    var id: String = "",
    var createdTime: Long = -1,
    var acidic: Float = -1f,
    var alcohol_ABV: Int = -1,
    var author: String = "",
    var price: Int = 0,
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
    var body: Float = -1f,
    var sweet: Float = -1f,
    var sour: Float = -1f,
    var take_again: Boolean = false,
    var bar: String = "",
    var name: String = "",
    var strong: Float = -1f
) : Parcelable {

}