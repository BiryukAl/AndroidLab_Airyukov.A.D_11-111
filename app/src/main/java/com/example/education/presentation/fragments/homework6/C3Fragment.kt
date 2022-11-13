package com.example.education.presentation.fragments.homework6

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentC3Binding

class C3Fragment: Fragment(R.layout.fragment_c_3) {

    private val viewBinding: FragmentC3Binding
            by viewBinding(FragmentC3Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnToC1.setOnClickListener{
            findNavController().navigate(R.id.action_c3Fragment_to_CFragment)
        }
    }
}