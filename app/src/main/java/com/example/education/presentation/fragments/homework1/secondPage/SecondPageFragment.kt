package com.example.education.presentation.fragments.homework1.secondPage

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentSecondPageBinding

class SecondPageFragment : Fragment(R.layout.fragment_second_page) {

    private val viewBinding: FragmentSecondPageBinding
            by viewBinding(FragmentSecondPageBinding::bind)


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val count: Int = arguments?.getInt(ARG_COUNTER_VALUE) ?: 0
        val countColor: Int = arguments?.getInt(ARG_COUNTER_COLOR) ?: 1


        if (count == 0) {
            viewBinding.boxCount.setTransitionVisibility(View.INVISIBLE)
        } else {
            viewBinding.boxCount.text = resources.getString(R.string.count_0, count.toString())
        }

        when (countColor % 3) {
            1 -> viewBinding.boxColor.setBackgroundResource(R.color.red)
            2 -> viewBinding.boxColor.setBackgroundResource(R.color.blue)
            0 -> viewBinding.boxColor.setBackgroundResource(R.color.green_600)
        }
    }


    companion object {

        const val SECOND_PAGE_FRAGMENT_TAG = "SECOND_PAGE_FRAGMENT_TAG"

        const val ARG_COUNTER_VALUE = "ARG_COUNTER_VALUE"

        const val ARG_COUNTER_COLOR = "ARG_COUNTER_COLOR"

        @JvmStatic
        fun getInstance(count: Int, countColor: Int): Fragment {
            val args = Bundle().apply {
                putInt(ARG_COUNTER_VALUE, count)
                putInt(ARG_COUNTER_COLOR, countColor)
            }

            val fragment = SecondPageFragment()
            fragment.arguments = args
            return fragment
        }

    }

}