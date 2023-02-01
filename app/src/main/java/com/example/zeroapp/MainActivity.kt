package com.example.zeroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import antuere.domain.dto.Settings
import antuere.domain.usecases.user_settings.GetSettingsUseCase
import com.example.zeroapp.databinding.ActivityMainBinding
import com.example.zeroapp.presentation.summary.SummaryViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private var _bottomNavView: BottomNavigationView? = null
    val bottomNavView: BottomNavigationView?
        get() = _bottomNavView

    var toolbar: MaterialToolbar? = null
    private var navController: NavController? = null

    private val viewModel by viewModels<SummaryViewModel>()

    @Inject
    lateinit var getSettingsUseCase: GetSettingsUseCase
    private var settings: Settings? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            settings = getSettingsUseCase(Unit).first()
        }

        installSplashScreen().apply {
            if (BuildConfig.BUILD_TYPE != "benchmark") {
                setKeepOnScreenCondition {
                    viewModel.isShowSplash.value!!
                }
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            setupBinding(it)
        }

    }

    private fun setupBinding(binding: ActivityMainBinding) {
        Timber.plant(Timber.DebugTree())

        toolbar = binding.toolBarApp
        setSupportActionBar(toolbar)
        _bottomNavView = binding.bottomView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment

        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.settingsFragment,
                R.id.historyFragment,
                R.id.summaryFragment,
            )
        )

        val navInflater = navController!!.navInflater
        val graph = navInflater.inflate(R.navigation.navigation)

        if (!settings!!.isPinCodeEnabled && !settings!!.isBiometricEnabled) {
            graph.setStartDestination(R.id.summaryFragment)
        } else {
            graph.setStartDestination(R.id.secureEntryFragment)
        }

        navController?.graph = graph

        navController?.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.settingsFragment -> {
                    showBottomBar()
                }
                R.id.historyFragment -> {
                    showBottomBar()
                }
                R.id.addDayFragment -> hideBottomBar()
                R.id.summaryFragment -> {
                    showUiElements()
                    navController!!.graph.setStartDestination(dest.id)
                }
                R.id.detailFragment -> hideBottomBar()
                R.id.favoritesFragment -> hideBottomBar()
                R.id.catsFragment -> hideBottomBar()
                R.id.loginFragment -> hideBottomBar()
                R.id.registerFragment -> hideBottomBar()
                R.id.resetPasswordFragment -> hideBottomBar()
                R.id.signInMethodsFragment -> hideBottomBar()
                R.id.mentalTipsFragment -> hideBottomBar()
                R.id.secureEntryFragment -> {
                    goneBottomBar()
                    goneAppBar()
                }
            }
        }

        setupActionBarWithNavController(navController!!, appBarConfiguration)
        _bottomNavView?.setupWithNavController(navController!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false
    }

    private fun hideBottomBar() {
        _bottomNavView?.visibility = View.INVISIBLE
    }

    private fun goneBottomBar() {
        _bottomNavView?.visibility = View.GONE
    }

    private fun showBottomBar() {
        _bottomNavView?.visibility = View.VISIBLE
    }

    private fun goneAppBar() {
        toolbar!!.visibility = View.GONE
    }

    private fun showUiElements() {
        lifecycleScope.launch {
            delay(100)
            toolbar!!.visibility = View.VISIBLE
            _bottomNavView?.visibility = View.VISIBLE
        }

    }
}