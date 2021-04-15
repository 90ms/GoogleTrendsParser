package com.a90ms.nowrtf.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.a90ms.nowrtf.R
import com.a90ms.nowrtf.databinding.ActivityMainBinding
import com.a90ms.nowrtf.ui.base.BaseActivity
import com.a90ms.nowrtf.util.gone
import com.a90ms.nowrtf.util.isValidDestination
import com.a90ms.nowrtf.util.show
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupData()
    }

    private fun setupData() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationChanged(destination.id)
        }

        binding.bottomNav.setOnNavigationItemSelectedListener(onBottomNavigationListener)
    }

    private val onBottomNavigationListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_setting -> {
                    val navOptions =
                        NavOptions.Builder().setPopUpTo(R.id.nav_host_fragment, true).build()
                    if (isNotSameDestination(R.id.SettingFragment)) {
                        Navigation.findNavController(this, R.id.nav_host_fragment)
                            .navigate(R.id.SettingFragment, null, navOptions)
                    }
                    true
                }
                else -> {
                    if (isNotSameDestination(R.id.ListFragment)) {
                        Navigation.findNavController(this, R.id.nav_host_fragment)
                            .navigate(R.id.ListFragment)
                    }
                    true
                }
            }
        }

    private fun bottomNavigationChanged(destinationId: Int) {
        when (destinationId) {
            R.id.ListFragment -> {
                binding.bottomNav.selectedItemId = R.id.nav_list
                binding.bottomNav.show()
            }
            R.id.SettingFragment -> {
                binding.bottomNav.selectedItemId = R.id.nav_setting
                binding.bottomNav.show()
            }
            else -> {
                binding.bottomNav.gone()
            }
        }
    }

    private fun isNotSameDestination(destination: Int): Boolean {
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment)
            .currentDestination?.id
    }

    override fun onBackPressed() {
        when {
            navController.isValidDestination(R.id.ListFragment) -> finish()
            navController.isValidDestination(R.id.SettingFragment) -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.nav_host_fragment, true).build()
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.ListFragment, null, navOptions)
            }
            else -> super.onBackPressed()
        }
    }
}

