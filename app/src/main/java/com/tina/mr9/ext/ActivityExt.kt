package com.tina.mr9.ext

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import com.tina.mr9.Mr9Application
import com.tina.mr9.factory.ViewModelFactory

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * Extension functions for Activity.
 */
fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as Mr9Application).repository
    return ViewModelFactory(repository)
}

fun Activity?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}