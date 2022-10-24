package com.example.education.presentation.fragments.homework3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.MainActivity
import com.example.education.R
import com.example.education.databinding.FragmentFirstHoWo3Binding
import com.example.education.databinding.FragmentFirstPageBinding
import com.example.education.presentation.fragments.homework1.firstPage.FirstPageFragment
import com.example.education.presentation.fragments.homework1.secondPage.SecondPageFragment

class FirstHoWo3Fragment : Fragment(R.layout.fragment_first_ho_wo_3) {

    private val viewBinding: FragmentFirstHoWo3Binding by viewBinding(FragmentFirstHoWo3Binding::bind)

    private var count: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            btnToThirdFragment.setOnClickListener {
                (requireActivity() as? MainActivity)?.apply {
                    replaceFragment(
                        SecondHoWo3Fragment.getInstance(),
                        SecondHoWo3Fragment.SECOND_HO_WO_3_FRAGMENT_TAG,
                    )

                    replaceFragment(
                        ThirdHoWo3Fragment.getInstance(count),
                        ThirdHoWo3Fragment.THIRD_HO_WO_3_FRAGMENT_TAG,
                    )
                }
            }

            btnRandomNumber.setOnClickListener {
                count = (1..100).random()
            }
        }


    }


    companion object {
        const val FIRST_HO_WO_3_FRAGMENT_TAG = "FIRST_HO_WO_3_FRAGMENT_TAG"

        fun getInstance() = FirstHoWo3Fragment()
    }
}