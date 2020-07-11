package com.tina.mr9.ext

import androidx.fragment.app.Fragment
import com.tina.mr9.Mr9Application
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.Search
import com.tina.mr9.factory.DrinksViewModelFactory
import com.tina.mr9.factory.ListViewModelFactory
import com.tina.mr9.factory.SearchItemViewModelFactory
import com.tina.mr9.factory.ViewModelFactory
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

fun Fragment.getVmFactory(drinks: Drinks): DrinksViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return DrinksViewModelFactory(repository, drinks)
}

fun Fragment.getVmFactory(searchTypeFilter: SearchTypeFilter): SearchItemViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return SearchItemViewModelFactory(repository, searchTypeFilter)
}

fun Fragment.getVmFactory(search: Search, type: SearchTypeFilter): ListViewModelFactory {
    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
    return ListViewModelFactory(repository, search, type)
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
