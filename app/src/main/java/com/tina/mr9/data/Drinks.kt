package com.tina.mr9.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */

data class Drinks(
    val name: String,
    val bar: String,
    val base: ArrayList<String>,
    val contents: ArrayList<String>,
    val pairings: ArrayList<String>,
    val category: String,
    val main_image: String,
    val tag: ArrayList<String>,
    val rating: HashMap<Ratings, Any> = hashMapOf()

) {
    lateinit var value: MutableList<Drinks>
}

@Parcelize
data class Ratings(
    val acidic: Long,
    val alcohol_ABV: Long,
    val author: String,
    val base: String,
    val pairings: ArrayList<String>,
    val category: String,
    val comment: String,
    val contents: ArrayList<String>,
    val created_time: Timestamp,
    val images: ArrayList<String>,
    val overall_rating: Long,
    val strong: Long,
    val sweet: Long,
    val take_again: Boolean

) : Parcelable
