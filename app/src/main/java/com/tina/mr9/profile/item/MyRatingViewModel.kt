package com.tina.mr9.profile.item

//import android.arch.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.data.User
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyRatingViewModel (private val stylishRepository: StylishRepository, private val arguments: User?) : ViewModel() {
    // After login to Mr.9 server through Google, at the same time we can get user info to provide to display ui
    private val _user = MutableLiveData<User>().apply {
        arguments?.let {
            value = it
            Logger.d("arguments.image = ${arguments.image}")
        }

    }

    val user: LiveData<User>
        get() = _user

}