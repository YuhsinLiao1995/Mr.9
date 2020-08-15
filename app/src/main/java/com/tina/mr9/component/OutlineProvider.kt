package com.tina.mr9.component

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.tina.mr9.Mr9Application
import com.tina.mr9.R

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class OutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        view.clipToOutline = true
        val radius =
            Mr9Application.instance.resources.getDimensionPixelSize(R.dimen.radius_profile_avatar)
        outline.setRoundRect(0, 0, view.right + radius, view.bottom, radius.toFloat())

    }
}

class OutlineProvider4Drink : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        view.clipToOutline = true
        val radius =
            Mr9Application.instance.resources.getDimensionPixelSize(R.dimen.radius_profile_avatar)
        outline.setRoundRect(0, 0, view.right, view.bottom + radius, radius.toFloat())

    }
}
