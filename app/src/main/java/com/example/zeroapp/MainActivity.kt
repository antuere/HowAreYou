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


// TODO Попробовать Cicerone вместо гугловой навигации
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var bottomNavView: BottomNavigationView? = null
    private var navController: NavController? = null

    var toolbar: MaterialToolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("fix! activityMain: onCrete")
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            setupBinding(it)
        }

    }

    private fun setupBinding(binding: ActivityMainBinding) {
        toolbar = binding.toolBarApp
        setSupportActionBar(toolbar)

        bottomNavView = binding.bottomView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment

        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.settingsFragment,
                R.id.historyFragment,
                R.id.summaryFragment,
                R.id.loginFragment
            )
        )

        navController?.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.settingsFragment -> showBottomBar()
                R.id.historyFragment -> showBottomBar()
                R.id.addDayFragment -> hideBottomBar()
                R.id.summaryFragment -> showBottomBar()
                R.id.detailFragment -> hideBottomBar()
                R.id.favoritesFragment -> hideBottomBar()
                R.id.catsFragment -> hideBottomBar()
                R.id.loginFragment -> hideBottomBar()
            }

        }

        setupActionBarWithNavController(navController!!, appBarConfiguration)
        bottomNavView?.setupWithNavController(navController!!)

        Timber.plant(Timber.DebugTree())
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false
    }

    private fun hideBottomBar() {
        bottomNavView?.visibility = View.INVISIBLE
    }

    private fun showBottomBar() {
        bottomNavView?.visibility = View.VISIBLE
    }
}