package com.example.appmovies.common.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun FragmentManager.findNavController(@IdRes containerId: Int): NavController {
    val navHostFragment = findFragmentById(containerId) as NavHostFragment
    return navHostFragment.navController
}