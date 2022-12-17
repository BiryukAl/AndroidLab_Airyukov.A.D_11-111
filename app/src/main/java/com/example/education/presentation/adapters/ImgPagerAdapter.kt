package com.example.education.presentation.adapters

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.education.presentation.fragments.homework9.ItemImgFragment

class ImgPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var listPages: List<String> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = listPages.size

    override fun createFragment(position: Int): Fragment {
        return ItemImgFragment.getInstance(listPages[position])
    }
}