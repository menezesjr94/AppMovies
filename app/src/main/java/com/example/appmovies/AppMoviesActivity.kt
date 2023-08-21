package com.example.appmovies

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appmovies.common.extensions.findNavController
import com.example.appmovies.databinding.ActivityAppMoviesBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppMoviesActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAppMoviesBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            supportFragmentManager.findNavController(R.id.nav_host_fragment_content_main)

        setSupportActionBar(binding.toolbar)
        configureBottomNavBar(navController)
        configureToolbar()

    }

    private fun configureBottomNavBar(navController: NavController) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navController.navigate(R.id.nav_graph)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val visibilityMap = mapOf(
                R.id.menu_movies_list to View.VISIBLE,
                R.id.menu_favorite_list to View.VISIBLE,
            )

            bottomNavigationView.visibility = visibilityMap[destination.id] ?: View.GONE
        }
        bottomNavigationView.setupWithNavController(navController)

    }

    private fun configureToolbar() {
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.menu_movies_list,
                R.id.menu_favorite_list,
            ),
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}