package com.example.education.presentation.fragments.homework5

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.MainActivity
import com.example.education.R
import com.example.education.databinding.FragmentC2Binding

class C2Fragment: Fragment(R.layout.fragment_c_2) {

    private val viewBinding: FragmentC2Binding
            by viewBinding(FragmentC2Binding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewBinding.btnToC3.setOnClickListener{

            (requireActivity() as? MainActivity)?.changeBottomNavigationVisibility(true)

            findNavController().navigate(R.id.action_c2Fragment_to_c3Fragment)


        }
    }
}