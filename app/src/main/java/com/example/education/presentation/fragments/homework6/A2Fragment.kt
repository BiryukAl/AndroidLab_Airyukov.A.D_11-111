package com.example.education.presentation.fragments.homework6

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentA2Binding

class A2Fragment: Fragment(R.layout.fragment_a_2) {

    private val viewBinding: FragmentA2Binding
            by viewBinding(FragmentA2Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnToA3.setOnClickListener{
            findNavController().navigate(R.id.action_a2Fragment_to_a3Fragment)
        }
    }
}