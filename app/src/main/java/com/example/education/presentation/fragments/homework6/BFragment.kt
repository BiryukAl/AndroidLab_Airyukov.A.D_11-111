package com.example.education.presentation.fragments.homework6

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentBBinding

class BFragment : Fragment(R.layout.fragment_b) {

    private val viewBinding: FragmentBBinding
            by viewBinding(FragmentBBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnToB2.setOnClickListener {

            val text = viewBinding.etB.text.toString()

            val args = Bundle().apply {
                putString(B2Fragment.EDIT_TEXT_FOR_TEXT_VIEW_TAG, text)
            }

            findNavController().navigate(R.id.action_BFragment_to_b2Fragment, args)
        }
    }
}