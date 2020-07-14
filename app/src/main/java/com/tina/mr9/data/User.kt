package com.tina.mr9.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Wayne Chen in Jul. 2019.
 */
@Parcelize
data class User(
    val id: String = "",
    val image: String = "",
    val name: String = "",
    val email: String = "",
    val followed_by: List<String> = emptyList(),
    val following: List<String> = emptyList(),
    val likedList: List<String> = emptyList()
) : Parcelable
