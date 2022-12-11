package com.example.education.presentation.fragments.homework9

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.education.R
import com.example.education.cache.ImgService
import com.example.education.databinding.FragmentHm9Binding
import com.example.education.presentation.adapters.ImgPagerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class Hm9Fragment : Fragment(R.layout.fragment_hm9) {
    private val viewBinding: FragmentHm9Binding
            by viewBinding(FragmentHm9Binding::bind)

    private var imgPagerAdapter: ImgPagerAdapter? = null

    private var glide: RequestManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glide = Glide.with(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgPagerAdapter = ImgPagerAdapter(this).apply {
            listPages = ImgService.listOfUrlLink
        }

        with(viewBinding) {
            viewPager.adapter = imgPagerAdapter

            btnPreloadImg.setOnClickListener {
                val listUrl = ImgService.listOfUrlLink
                val sizeList = listUrl.size

                val point1 = (sizeList + 1) / 3
                val point2 = ((sizeList + 1) / 3) * 2

                lifecycleScope.async(Dispatchers.IO) {
                    preloadImg(listUrl.subList(0, point1))
                    preloadImg(listUrl.subList(point1, point2))
                    preloadImg(listUrl.subList(point2, sizeList))
                }

            }
        }
    }

    suspend fun preloadImg(listUrl: List<String>) {
        withContext(Dispatchers.IO) {
            listUrl.forEach {
                glide?.load(it)
                    ?.preload()
                Log.d("MyGlideTest", "BTN download $it")

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        glide = null
    }

    companion object {
        const val HM9_FRAGMENT_TAG = "HM9_FRAGMENT_TAG"

        fun getInstance() = Hm9Fragment()
    }
}