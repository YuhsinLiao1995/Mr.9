package com.tina.mr9.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Wayne Chen in Jul. 2019.
 */
@Parcelize
data class User(
    var uid: String = "",
    var image: String = "",
    var name: String = "",
    var email: String = "",
    var myPost: List<String> = emptyList(),
    var followed_by: List<String> = emptyList(),
    var following: List<String> = emptyList(),
    var likedList: List<String> = emptyList()
) : Parcelable
