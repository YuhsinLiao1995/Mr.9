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
    val name: String = "",
    val address: String = "",
    val amt_rating: Long = -1,
    val avg_rating: Long = -1,
    val drinks: List<String> = emptyList(),
    val images: List<String> = emptyList(),
    val pairings: List<String> = emptyList(),
    val food: List<String> = emptyList(),
    val main_image: String = "",
    val min_charge: Long = -1,
    val type_bar: List<String> = emptyList()

) : Parcelable {


}

