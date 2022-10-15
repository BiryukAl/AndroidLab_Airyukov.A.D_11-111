package com.example.education.fragments.homework3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentThirdHoWo3Binding
import com.example.education.fragments.homework1.secondPage.SecondPageFragment

class ThirdHoWo3Fragment : Fragment(R.layout.fragment_third_ho_wo_3) {

    private var countColor: Int = 1

    private val viewBinding: FragmentThirdHoWo3Binding by viewBinding(FragmentThirdHoWo3Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val count: Int = arguments?.getInt(SecondPageFragment.ARG_COUNTER_VALUE) ?: 0
        if (count != 0) {
            viewBinding.tvText.text = count.toString()
        } else {
            viewBinding.tvText.text =
                "Jakarta EE. В 2018 Eclipse Foundation переименовала Java EE в Jakarta EE — набор спецификаций и соответствующей документации для языка Java, описывающей архитектуру серверной платформы для задач средних и крупных предприятий."
            // Не ругайте за то что не в Стрингах )))

        }
    }

    override fun onStop() {
        super.onStop()
        countColor++
    }

    override fun onResume() {
        super.onResume()
        when (countColor % 3) {
            1 -> viewBinding.mainConstraintLayout.setBackgroundResource(R.color.red)
            2 -> viewBinding.mainConstraintLayout.setBackgroundResource(R.color.purple_200)
            0 -> viewBinding.mainConstraintLayout.setBackgroundResource(R.color.green_600)
        }
    }

    companion object {
        const val THIRD_HO_WO_3_FRAGMENT_TAG = "THIRD_HO_WO_3_FRAGMENT_TAG"

        fun getInstance(count: Int): Fragment {

            val args = Bundle().apply {
                putInt(SecondPageFragment.ARG_COUNTER_VALUE, count)
            }

            val fragment = ThirdHoWo3Fragment()
            fragment.arguments = args
            return fragment
        }
    }
}