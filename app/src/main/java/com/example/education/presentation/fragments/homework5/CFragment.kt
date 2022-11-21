package com.example.education.presentation.fragments.homework5

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.presentation.MainActivity
import com.example.education.R
import com.example.education.databinding.FragmentCBinding


class CFragment : Fragment(R.layout.fragment_c) {

    private val viewBinding: FragmentCBinding
            by viewBinding(FragmentCBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewBinding.btnToC2.setOnClickListener {

            (requireActivity() as? MainActivity)?.changeBottomNavigationVisibility(false)

            findNavController().navigate(R.id.action_CFragment_to_c2Fragment)

        }

    }
}