package com.example.zeroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.zeroapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavView = binding.bottomView
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.settingsFragment,
                R.id.historyFragment,
                R.id.titleFragment,
                R.id.summaryFragment
            )
        )

        navController.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.settingsFragment -> showBottomBar()
                R.id.historyFragment -> showBottomBar()
                R.id.titleFragment -> showBottomBar()
                R.id.summaryFragment -> showBottomBar()
                R.id.detailFragment -> hideBottomBar()
            }

        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
        Timber.plant(Timber.DebugTree())
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun hideBottomBar() {
        bottomNavView.visibility = View.GONE
    }

    private fun showBottomBar() {
        bottomNavView.visibility = View.VISIBLE
    }
}