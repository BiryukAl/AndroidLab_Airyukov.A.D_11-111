package com.example.education.presentation.fragments.homework4

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentDescriptionPlanetBinding
import com.example.education.presentation.cache.PlanetService

class DescriptionPlanetFragment : Fragment(R.layout.fragment_description_planet) {
    private val viewBinding: FragmentDescriptionPlanetBinding by viewBinding(
        FragmentDescriptionPlanetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemIndex: Int = arguments?.getInt(ITEM_POSITION_TAG) ?: 0
        val planet = PlanetService.listOfPlanets[itemIndex]

        with(viewBinding) {
            title.text = planet.name
            description.text = planet.description
        }
    }


    companion object {
        const val DESCRIPTION_PLANET_FRAGMENT_TAG = "DESCRIPTION_PLANET_FRAGMENT_TAG"

        const val ITEM_POSITION_TAG = "ITEM_POSITION_TAG"

        fun getInstance(itemPosition: Int): Fragment {

            val args = Bundle().apply {
                putInt(ITEM_POSITION_TAG, itemPosition)
            }

            val fragment = DescriptionPlanetFragment()
            fragment.arguments = args
            return fragment
        }
    }
}