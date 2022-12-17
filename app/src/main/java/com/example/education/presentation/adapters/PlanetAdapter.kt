package com.example.education.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.education.R
import com.example.education.databinding.ItemPlanetBinding
import com.example.education.presentation.model.Planet

class PlanetAdapter() :
    RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder>() {

    var listPlanets: List<Planet> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PlanetAdapter.PlanetViewHolder =
        PlanetViewHolder(viewBinding = ItemPlanetBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))


    override fun onBindViewHolder(holder: PlanetAdapter.PlanetViewHolder, position: Int) {
        holder.bindItem(listPlanets[position])
    }

    override fun getItemCount(): Int = listPlanets.size


    inner class PlanetViewHolder(
        private val viewBinding: ItemPlanetBinding,
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        init {
            with(viewBinding) {
                root.setOnClickListener {
                    onItemClickListener?.invoke(bindingAdapterPosition)
                    notifyItemChanged(bindingAdapterPosition)
                }
            }
        }

        fun bindItem(planet: Planet) {
            if (planet.isWatching) {
                viewBinding.root.setBackgroundColor(ContextCompat.getColor(itemView.context,
                    R.color.grey_200))
            }
            with(viewBinding) {
                tvPlanetName.text = planet.name
            }
        }
    }
}

