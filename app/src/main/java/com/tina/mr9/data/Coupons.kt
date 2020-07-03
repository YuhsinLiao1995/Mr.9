package com.tina.mr9.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Wayne Chen in Jul. 2019.
 */
@Parcelize
data class Coupons(
    val title: String,
    val amount: Int
) : Parcelable
