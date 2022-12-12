package com.example.education.presentation.fragments.homework9

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.education.R
import com.example.education.databinding.FragmentItemImgBinding
import kotlinx.coroutines.*

class ItemImgFragment : Fragment(R.layout.fragment_item_img) {
    private val viewBinding: FragmentItemImgBinding
            by viewBinding(FragmentItemImgBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString(ARG_URL_LINK)
            ?: "https://s1.1zoom.ru/b6755/973/Cats_Kittens_Grey_Glance_Wicker_basket_517968_2048x1152.jpg"


        lifecycleScope.async(Dispatchers.IO) {
            downloadImg(url)
            withContext(Dispatchers.Main) {
                Glide.with(this@ItemImgFragment)
                    .load(url)
                    .into(viewBinding.imageItem)
                viewBinding.progressBar.visibility = View.GONE
                Log.d("MyGlideTest", "Fragment")

            }
        }
    }

    suspend fun downloadImg(url: String) {
        delay(500L)
        Glide.with(this)
            .load(url)
            .preload()
    }

    companion object {
        const val ARG_URL_LINK = "ARG_URL_LINK"

        fun getInstance(url: String): Fragment {

            val args = Bundle().apply {
                putString(ARG_URL_LINK, url)
            }

            val fragment = ItemImgFragment()
            fragment.arguments = args
            return fragment
        }

    }
}