package com.rewardtodo.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rewardtodo.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)

        // Only show bottom nav bar for the 3 primary screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_todolist, R.id.navigation_rewards, R.id.navigation_settings -> showBottomNavBar()
                else -> hideBottomNavBar()
            }
            getCurrentFragment()?.onNavigateAway()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        getCurrentFragment()?.dispatchTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun getCurrentFragment(): BaseFragment? {
        nav_host_fragment?.childFragmentManager?.let {
            if (nav_host_fragment.childFragmentManager.fragments.isNotEmpty())
                return nav_host_fragment.childFragmentManager.fragments[0] as BaseFragment
        }
        return null
    }

    fun showBottomNavBar() {
        nav_view.visibility = View.VISIBLE
    }

    fun hideBottomNavBar() {
        nav_view.visibility = View.GONE
    }
}