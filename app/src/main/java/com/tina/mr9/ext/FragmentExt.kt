package com.tina.mr9.ext

import androidx.fragment.app.Fragment
import com.tina.mr9.Mr9Application
import com.tina.mr9.data.Drinks
import com.tina.mr9.factory.DrinksViewModelFactory
import com.tina.mr9.factory.ViewModelFactory

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



//fun Fragment.getVmFactory(user: User?): ProfileViewModelFactory {
//    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
//    return ProfileViewModelFactory(repository, user)
//}
//
//fun Fragment.getVmFactory(product: Product): ProductViewModelFactory {
//    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
//    return ProductViewModelFactory(repository, product)
//}
//
//fun Fragment.getVmFactory(catalogType: CatalogTypeFilter): CatalogItemViewModelFactory {
//    val repository = (requireContext().applicationContext as Mr9Application).stylishRepository
//    return CatalogItemViewModelFactory(repository, catalogType)
//}