package com.example.education.fragments.fourRectangles

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentFourRectanglesBinding

class FourRectanglesFragment : Fragment(R.layout.fragment_four_rectangles) {

    private val viewBinding: FragmentFourRectanglesBinding by viewBinding(FragmentFourRectanglesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {

            (oneRectangle.layoutParams as? ConstraintLayout.LayoutParams)?.apply {

                endToEnd = ConstraintLayout.LayoutParams.UNSET
                startToStart = ConstraintLayout.LayoutParams.UNSET
                topToTop = ConstraintLayout.LayoutParams.UNSET
                topToBottom = ConstraintLayout.LayoutParams.UNSET
                height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_SPREAD



                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID

                endToStart = twoRectangle.id
                bottomToTop = treeRectangle.id

            }
            (twoRectangle.layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                endToEnd = ConstraintLayout.LayoutParams.UNSET
                startToStart = ConstraintLayout.LayoutParams.UNSET
                topToBottom = ConstraintLayout.LayoutParams.UNSET
                height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_SPREAD




                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                startToEnd = oneRectangle.id
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToTop = fourRectangle.id
            }
            (treeRectangle.layoutParams as? ConstraintLayout.LayoutParams)?.apply {

                endToEnd = ConstraintLayout.LayoutParams.UNSET
                startToStart = ConstraintLayout.LayoutParams.UNSET
                topToBottom = ConstraintLayout.LayoutParams.UNSET
                height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_SPREAD




                endToStart = fourRectangle.id
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToBottom = twoRectangle.id
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }
            (fourRectangle.layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                endToEnd = ConstraintLayout.LayoutParams.UNSET
                startToStart = ConstraintLayout.LayoutParams.UNSET
                topToBottom = ConstraintLayout.LayoutParams.UNSET
                height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_SPREAD


                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                startToEnd = treeRectangle.id
                topToBottom = twoRectangle.id
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }


        }
    }


    companion object {
        const val FOUR_RECTANGLES_TAG = "FOUR_RECTANGLES_TAG"

        fun getInstance() = FourRectanglesFragment()
    }
}