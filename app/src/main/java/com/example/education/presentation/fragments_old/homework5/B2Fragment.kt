package com.example.education.presentation.fragments_old.homework5

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentB2Binding

class B2Fragment: Fragment(R.layout.fragment_b_2) {

    private val viewBinding: FragmentB2Binding
            by viewBinding(FragmentB2Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var text = arguments?.getString(EDIT_TEXT_FOR_TEXT_VIEW_TAG) ?: "Введите текст на предыдущем экране <--"

        if (text.isEmpty()){
            text = "Введите текст на предыдущем экране <--"
        }

        viewBinding.tvTextForPreviousPage.text = text

    }



    companion object{
        const val EDIT_TEXT_FOR_TEXT_VIEW_TAG = "EDIT_TEXT_FOR_TEXT_VIEW_TAG"


    }
}