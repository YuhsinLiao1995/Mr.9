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
    val rating: Ratings = Ratings()

) : Parcelable {
    lateinit var value: MutableList<Drinks>
    @IgnoredOnParcel
    val contentsText: String = "contents : $bar"

}

@Parcelize
data class Ratings(
    val acidic: Long = -1,
    val alcohol_ABV: Long = -1,
    val author: String = "",
    val base: List<String> = emptyList(),
    val pairings: List<String> = emptyList(),
    val category: String = "",
    val comment: String = "",
    val contents: List<String> = emptyList(),
    val created_time: Long = -1,
    val images: List<String> = emptyList(),
    val overall_rating: Long = -1,
    val strong: Long = -1,
    val sweet: Long = -1,
    val take_again: Boolean = false
) : Parcelable{
//
//    var sizeRange = for (i = 0), i++){
//
//    }
//        if (sizes.size == 1) {
//        sizes[0]
//    } else {
//        "${sizes[0]} - ${sizes[sizes.size - 1]}"
//    }
}
