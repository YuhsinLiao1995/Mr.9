package com.tina.mr9.ext

import android.graphics.Rect
import android.icu.text.SimpleDateFormat
import android.util.DisplayMetrics
import android.view.TouchDelegate
import android.view.View
import com.tina.mr9.data.OrderProduct
import com.tina.mr9.data.Product
import com.tina.mr9.Mr9Application
import java.util.*

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Provides [List] [Product] to convert to [List] [OrderProduct] format
 */
fun Long.toDisplayFormat(): String {
    return SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.TAIWAN).format(this)
}

fun List<Product>?.toOrderProductList(): List<OrderProduct> {
    val orderProducts = mutableListOf<OrderProduct>()
    this?.apply {
        for (product in this) {
            orderProducts.add(
                OrderProduct(
                    product.id,
                    product.title,
                    product.price,
                    product.colors.filter { it.code == product.selectedVariant.colorCode }.first(),
                    product.selectedVariant.size,
                    product.amount ?: 0
                )
            )
        }
    }
    return orderProducts
}

/**
 * Increase touch area of the view/button .
 */
fun View.setTouchDelegate() {
    val parent = this.parent as View  // button: the view you want to enlarge hit area
    parent.post {
        val rect = Rect()
        this.getHitRect(rect)
        rect.top -= 100    // increase top hit area
        rect.left -= 100   // increase left hit area
        rect.bottom += 100 // increase bottom hit area
        rect.right += 100  // increase right hit area
        parent.touchDelegate = TouchDelegate(rect, this)
    }
}

fun Number.toDp(): Float {
    return this.toFloat() / (Mr9Application.instance.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}