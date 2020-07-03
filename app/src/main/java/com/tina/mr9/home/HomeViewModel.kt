package com.tina.mr9.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.data.HomeItem
import com.tina.mr9.data.Product
import com.tina.mr9.data.Result
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Util.getString
import com.tina.mr9.R
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [HomeFragment].
 */
class HomeViewModel(private val stylishRepository: StylishRepository) : ViewModel() {
}