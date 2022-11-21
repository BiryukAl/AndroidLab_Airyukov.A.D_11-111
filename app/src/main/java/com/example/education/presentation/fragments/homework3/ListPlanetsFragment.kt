package com.example.education.presentation.fragments.homework3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.presentation.MainActivity
import com.example.education.R
import com.example.education.databinding.FragmentListPlanetsBinding
import com.example.education.presentation.adapters.PlanetAdapter
import com.example.education.cache.PlanetService

class ListPlanetsFragment : Fragment(R.layout.fragment_list_planets) {
    private val viewBinding: FragmentListPlanetsBinding by viewBinding(FragmentListPlanetsBinding::bind)

    private var planetAdapter: PlanetAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planetAdapter = PlanetAdapter().apply {
            listPlanets = PlanetService.listOfPlanets
            onItemClickListener = ::onItemClicked
        }

        val manager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewBinding.recyclerViewPlanets.apply {
            layoutManager = manager
            adapter = planetAdapter
        }
    }

    override fun onDestroyView() {
        viewBinding.recyclerViewPlanets.adapter = null
        planetAdapter = null
        super.onDestroyView()

    }


    private fun onItemClicked(itemPosition: Int) {
        if (!PlanetService.listOfPlanets[itemPosition].isWatching)
            PlanetService.listOfPlanets[itemPosition].isWatching = true
//        оно тут не должно находится но я не знаю куда его поместить

        planetAdapter?.notifyItemChanged(itemPosition)

        (requireActivity() as? MainActivity)?.replaceFragment(
            DescriptionPlanetFragment.getInstance(itemPosition = itemPosition),
            DescriptionPlanetFragment.DESCRIPTION_PLANET_FRAGMENT_TAG
        )

    }


    companion object {
        const val LIST_PLANETS_FRAGMENT_TAG = "LIST_PLANETS_FRAGMENT_TAG"

        fun getInstance() = ListPlanetsFragment()
    }
}