package com.raf.mynavdrawerxml

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.raf.mynavdrawerxml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)
        setupNavigation()
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    private fun setupNavigation() {
        // Navigation Drawer (Side Menu)
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.about_menu -> {
                    navController.navigate(R.id.aboutFragment)
                    supportActionBar?.title = getString(R.string.about)
                }

                else -> menuItem.isChecked = false
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Bottom Navigation
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> navController.navigate(R.id.homeFragment)
                R.id.setting_menu -> navController.navigate(R.id.settingFragment)
            }
            true
        }

        // NavController Changes Listener
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = getString(R.string.app_name)
            supportActionBar?.show()
            settingBottomBar(72, true)

            when (destination.id) {
                R.id.homeFragment -> {
                    binding.bottomNav.menu.findItem(R.id.home_menu)?.isChecked = true
                }

                R.id.settingFragment -> {
                    binding.bottomNav.menu.findItem(R.id.setting_menu)?.isChecked = true
                    supportActionBar?.title = getString(R.string.settings)
                }

                R.id.addFragment -> {
                    supportActionBar?.hide()
                    settingBottomBar(0, false)
                }

                else -> {
                    settingBottomBar(0, false)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    private fun settingBottomBar(margin: Int, visibility: Boolean) {
        val marginInPixels = (margin * resources.displayMetrics.density).toInt()
        val layoutParams =
            binding.drawerLayout.layoutParams as ViewGroup.MarginLayoutParams

        layoutParams.bottomMargin = marginInPixels
        binding.drawerLayout.layoutParams = layoutParams
        binding.bottomNav.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}