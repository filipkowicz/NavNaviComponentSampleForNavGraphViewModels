package com.filipkowicz.navcomponentsample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.filipkowicz.navcomponentsample.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        ),
            fallbackOnNavigateUpListener = {
                Log.e("MFP", "fallback !!")
                false
            })
        setupActionBarWithNavController(navController, appBarConfiguration)

        /*NavigationUI.*/setupWithNavController(navView, navController)
    }
}


fun setupWithNavController(
    navigationBarView: NavigationBarView,
    navController: NavController
) {
    navigationBarView.setOnItemSelectedListener { item ->
        onNavDestinationSelected(
            item,
            navController
        )
    }
    val weakReference = WeakReference(navigationBarView)
    navController.addOnDestinationChangedListener(
        object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                val view = weakReference.get()
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this)
                    return
                }
                view.menu.forEach { item ->
                    if (destination.matchDestination(item.itemId)) {
                        item.isChecked = true
                    }
                }
            }
        })
}


public fun onNavDestinationSelected(item: MenuItem, navController: NavController): Boolean {
    val builder = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true)
    if (
        navController.currentDestination!!.parent!!.findNode(item.itemId)
                is ActivityNavigator.Destination
    ) {
        builder.setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
    } else {
        builder.setEnterAnim(androidx.navigation.ui.R.animator.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.animator.nav_default_exit_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.animator.nav_default_pop_enter_anim)
            .setPopExitAnim(androidx.navigation.ui.R.animator.nav_default_pop_exit_anim)
    }
    if (item.order and Menu.CATEGORY_SECONDARY == 0) {
        navController.backQueue.forEach {
            Log.e("MFP", it.destination.displayName)
        }
        builder.setPopUpTo(
            navController.graph.id,
            inclusive = false,
            saveState = true
        )
    }
    val options = builder.build()
    return try {
        // TODO provide proper API instead of using Exceptions as Control-Flow.
        navController.navigate(item.itemId, null, options)
        // Return true only if the destination we've navigated to matches the MenuItem
        navController.currentDestination?.matchDestination(item.itemId) == true
    } catch (e: IllegalArgumentException) {
        false
    }
}

internal fun NavDestination.matchDestination(@IdRes destId: Int): Boolean =
    hierarchy.any { it.id == destId }