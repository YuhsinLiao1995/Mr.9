package com.tina.mr9.data

import android.os.Parcelable
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

