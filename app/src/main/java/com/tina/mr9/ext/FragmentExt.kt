package com.tina.mr9.ext

import androidx.fragment.app.Fragment
import com.tina.mr9.Mr9Application
import com.tina.mr9.data.*
import com.tina.mr9.factory.*
import com.tina.mr9.search.SearchTypeFilter

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(drink: Drink?, rating: Rating?): DrinkViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return DrinkViewModelFactory(repository, drink, rating)
}

fun Fragment.getVmFactory(searchTypeFilter: SearchTypeFilter): SearchItemViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return SearchItemViewModelFactory(repository, searchTypeFilter)
}

fun Fragment.getVmFactory(search: Search, type: SearchTypeFilter): ListViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return ListViewModelFactory(repository, search, type)
}

fun Fragment.getVmFactory(user: User, follow: Boolean): FollowViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return FollowViewModelFactory(repository, user, follow)
}

fun Fragment.getVmFactory(bar: Bar): BarViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return BarViewModelFactory(repository, bar)
}

fun Fragment.getVmFactory(drink: Drink?): RateViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return RateViewModelFactory(repository, drink)
}

fun Fragment.getVmFactory(user: User?): ProfileViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return ProfileViewModelFactory(repository, user)
}

fun Fragment.getVmFactory(searchUser: User?, rating: Rating?): OthersProfileViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return OthersProfileViewModelFactory(repository, searchUser, rating)
}

fun Fragment.getVmFactory(rating: Rating): RatingsViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).repository
    return RatingsViewModelFactory(repository, rating)
}



