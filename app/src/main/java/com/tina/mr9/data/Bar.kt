package com.tina.mr9.data

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
@Parcelize
data class Bar(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var amt_rating: Long = -1,
    var amtRating: Long = -1,
    var overallRating: Float = -1f,
    var description: String = "",
    var drinks: List<String> = emptyList(),
    var images: List<String> = emptyList(),
    var pairings: List<String> = emptyList(),
    var food: List<String> = emptyList(),
    var main_image: String = "",
    var min_charge: Long = -1,
    var type_bar: List<String> = emptyList(),
    var likedBy: List<String> = emptyList(),
    var createdTime: Long = -1

) : Parcelable {

}

