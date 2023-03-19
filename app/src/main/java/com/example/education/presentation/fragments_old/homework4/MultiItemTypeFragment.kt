package com.example.education.presentation.fragments_old.homework4

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.education.R

class MultiItemTypeFragment : Fragment(R.layout.fragment_multi_item_type) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        const val MULTI_ITEM_TYPE_FRAGMENT_TAG = "MULTI_ITEM_TYPE_FRAGMENT_TAG"

        fun getInstance(): Fragment {

            val args = Bundle().apply {
            }

            val fragment = MultiItemTypeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}