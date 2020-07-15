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
    val name: String = "",
    val bar: String = "",
    val base: List<String> = emptyList(),
    val contents: List<String> = emptyList(),
    val pairings: List<String> = emptyList(),
    val category: String = "",
    val main_image: String = "",
    val images: List<String> = emptyList(),
    val tag: List<String> = emptyList(),
    val rating: Ratings = Ratings(),
    val createdTime: Long = -1

) : Parcelable {
    lateinit var value: MutableList<Drinks>
    @IgnoredOnParcel
    val contentsText: String = "contents : $bar"

}

@Parcelize
data class Ratings(
    var id: String = "",
    var createdTime: Long = -1,
    var acidic: Long = -1,
    var alcohol_ABV: Long = -1,
    var author: String = "",
    var main_photo: String = "",
    var base: List<String> = emptyList(),
    var pairings: List<String> = emptyList(),
    var category: String = "",
    var comment: String = "",
    var contents: List<String> = emptyList(),
    var created_time: Long = -1,
    var images: List<String> = emptyList(),
    var overall_rating: Long = -1,
    var strong: Long = -1,
    var sweet: Long = -1,
    var take_again: Boolean = false,
    var bar: String = "",
    var name: String = ""
) : Parcelable{
}
