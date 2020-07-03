package com.tina.mr9.data

import android.os.Parcelable
import com.tina.mr9.data.Product
import kotlinx.android.parcel.Parcelize

/**
 * Created by Wayne Chen in Jul. 2019.
 */
@Parcelize
data class Hots(
    val title: String,
    val products: List<Product>
) : Parcelable
