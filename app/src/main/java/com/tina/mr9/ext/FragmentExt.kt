package com.tina.mr9.ext

import androidx.fragment.app.Fragment
import com.tina.mr9.Mr9Application
import com.tina.mr9.data.*
import com.tina.mr9.factory.*
import com.tina.mr9.profile.ProfileFragment
import com.tina.mr9.search.SearchTypeFilter

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(drinks: Drinks?, ratings: Ratings?): DrinksViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return DrinksViewModelFactory(repository, drinks, ratings)
}

fun Fragment.getVmFactory(searchTypeFilter: SearchTypeFilter): SearchItemViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return SearchItemViewModelFactory(repository, searchTypeFilter)
}

fun Fragment.getVmFactory(search: Search, type: SearchTypeFilter): ListViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return ListViewModelFactory(repository, search, type)
}

fun Fragment.getVmFactory(user: User, follow: Boolean): FollowViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return FollowViewModelFactory(repository, user, follow)
}

fun Fragment.getVmFactory(bar: Bar): BarViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return BarViewModelFactory(repository, bar)
}

fun Fragment.getVmFactory(drinks: Drinks?): RateViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return RateViewModelFactory(repository, drinks)
}

//fun Fragment.getVmFactory(user: User): AuthorViewModelFactory {
//    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
//    return AuthorViewModelFactory(repository, user)
//}

fun Fragment.getVmFactory(user: User?): ProfileViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return ProfileViewModelFactory(repository, user)
}

fun Fragment.getVmFactory(searchUser: User?, ratings: Ratings?): OthersProfileViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return OthersProfileViewModelFactory(repository, searchUser, ratings)
}

fun Fragment.getVmFactory(ratings: Ratings): RatingsViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return RatingsViewModelFactory(repository, ratings)
}


//fun Fragment.getVmFactory(user: User?): ProfileViewModelFactory {
//    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
//    return ProfileViewModelFactory(repository, user)
//}
//
//fun Fragment.getVmFactory(product: Product): ProductViewModelFactory {
//    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
//    return ProductViewModelFactory(repository, product)
//}
