package com.example.education.presentation.fragments.homework5

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentA3Binding

class A3Fragment: Fragment(R.layout.fragment_a_3) {

    private val viewBinding: FragmentA3Binding
            by viewBinding(FragmentA3Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnToA1.setOnClickListener{
            findNavController().navigate(R.id.action_a3Fragment_to_AFragment)
        }


    }

}