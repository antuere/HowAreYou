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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navController: NavController

    private lateinit var _toolbar: MaterialToolbar
    val toolbar: MaterialToolbar
        get() = _toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("fix! activityMain: onCrete")
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
                R.id.summaryFragment
            )
        )

        navController.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.settingsFragment -> showBottomBar()
                R.id.historyFragment -> showBottomBar()
                R.id.addDayFragment -> hideBottomBar()
                R.id.summaryFragment -> showBottomBar()
                R.id.detailFragment -> hideBottomBar()
            }

        }

        _toolbar = binding.toolBarApp

        setSupportActionBar(_toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavView.setupWithNavController(navController)
        Timber.plant(Timber.DebugTree())
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun hideBottomBar() {
        bottomNavView.visibility = View.INVISIBLE
    }

    private fun showBottomBar() {
        bottomNavView.visibility = View.VISIBLE
    }
}