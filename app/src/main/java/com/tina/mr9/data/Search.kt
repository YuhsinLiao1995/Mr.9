package com.tina.mr9.data

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
@Parcelize
data class Search(
    val id: String = "",
    var name: String = "",
    val main_image: String = ""

) : Parcelable {

}

@Parcelize
data class User(
    var uid: String = "",
    var image: String = "",
    var name: String = "",
    var email: String = "",
    var createdTime: Long = -1,
    var myPost: List<String> = emptyList(),
    var followedBy: List<String> = emptyList(),
    var following: List<String> = emptyList(),
    var likedList: List<String> = emptyList()

) : Parcelable {

    @IgnoredOnParcel
    var amtFollowedBy: String = followedBy.size.toString()
    @IgnoredOnParcel
    var amtFollowing: String = following.size.toString()
}
