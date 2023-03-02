package com.example.education.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.data.bd.local.DatabaseHandler
import com.example.education.databinding.ActivityMainBinding
import com.example.education.presentation.fragments.homework11.MainWeatherFragment
import com.example.education.presentation.fragments.homework6.CreateNotifyFragment
import com.example.education.presentation.fragments.homework8.CreateForegroundServiceFragment
import com.example.education.presentation.fragments.homework9.Hm9Fragment


class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private val fragmentsContainerId: Int = R.id.main_fragments_container


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DatabaseHandler.dbInitialize(applicationContext)

/*        val navController =
            (supportFragmentManager.findFragmentById(fragmentsContainerId) as NavHostFragment).navController
        viewBinding.mainBottomNav.setupWithNavController(navController)*/

        supportFragmentManager.beginTransaction()
            .add(
                fragmentsContainerId,
                MainWeatherFragment.getInstance(),
                MainWeatherFragment.MAIN_WEATHER_FRAGMENT_TAG
            )
            .commit()
    }

    fun addFragment(fragment: Fragment, tag: String, detachCurrent: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()

        if (detachCurrent) {
            supportFragmentManager.findFragmentById(fragmentsContainerId)?.let { currentFragment ->
                transaction.detach(currentFragment)
            }
        }

        transaction
            .add(fragmentsContainerId, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    fun addWithRemove(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.findFragmentById(fragmentsContainerId)?.let { currentFragment ->
            transaction.remove(currentFragment)
        }
        transaction
            .add(fragmentsContainerId, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentsContainerId, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    fun changeBottomNavigationVisibility(isVisible: Boolean) {
//        viewBinding.mainBottomNav.visibility = if (isVisible) View.VISIBLE else View.GONE
    }


}