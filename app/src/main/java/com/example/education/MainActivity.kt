package com.example.education

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.databinding.ActivityMainBinding
import com.example.education.fragments.homework2.inputNumber.InputNumberFragment
import com.example.education.fragments.homework3.FirstHoWo3Fragment
import com.example.education.fragments.homework3.SecondHoWo3Fragment
import com.example.education.fragments.homework3.ThirdHoWo3Fragment

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private val fragmentsContainerId: Int = R.id.main_fragments_container


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(FirstHoWo3Fragment(), FirstHoWo3Fragment.FIRST_HO_WO_3_FRAGMENT_TAG, true)
        replaceFragment(SecondHoWo3Fragment(), SecondHoWo3Fragment.SECOND_HO_WO_3_FRAGMENT_TAG,)
        replaceFragment(ThirdHoWo3Fragment(), ThirdHoWo3Fragment.THIRD_HO_WO_3_FRAGMENT_TAG,)

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



}