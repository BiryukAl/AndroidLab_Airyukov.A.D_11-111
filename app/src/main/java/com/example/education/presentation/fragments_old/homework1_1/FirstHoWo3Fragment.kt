package com.example.education.presentation.fragments_old.homework1_1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.presentation.MainActivity
import com.example.education.R
import com.example.education.databinding.FragmentFirstHoWo11Binding

class FirstHoWo3Fragment : Fragment(R.layout.fragment_first_ho_wo_1_1) {

    private val viewBinding: FragmentFirstHoWo11Binding by viewBinding(FragmentFirstHoWo11Binding::bind)

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