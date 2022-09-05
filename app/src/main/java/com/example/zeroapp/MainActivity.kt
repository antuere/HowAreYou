package com.example.zeroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.zeroapp.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavView = binding.bottomView
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.settingsFragment,
                R.id.historyFragment,
                R.id.titleFragment,
                R.id.summaryFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
        Timber.plant(Timber.DebugTree())
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}