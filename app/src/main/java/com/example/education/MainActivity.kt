package com.example.education

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    val fragmentsContainerId: Int = R.id.main_fragments_container


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





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
            .commit()
    }



}