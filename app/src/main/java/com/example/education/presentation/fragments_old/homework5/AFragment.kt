package com.example.education.presentation.fragments_old.homework5

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentABinding

class AFragment : Fragment(R.layout.fragment_a) {

    private val viewBinding: FragmentABinding
            by viewBinding(FragmentABinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val options = navOptions {
            launchSingleTop = false
            popUpTo(R.id.AFragment) {
                inclusive = true
            }

        }

        viewBinding.btnToA2.setOnClickListener {
            findNavController().navigate(R.id.action_AFragment_to_a2Fragment, bundleOf(), options)
        }
    }

}
