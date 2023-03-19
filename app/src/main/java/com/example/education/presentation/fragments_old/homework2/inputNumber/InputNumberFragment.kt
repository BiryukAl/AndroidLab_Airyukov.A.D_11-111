package com.example.education.presentation.fragments_old.homework2.inputNumber

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.presentation.MainActivity
import com.example.education.R
import com.example.education.databinding.FragmentInputNumberBinding
import com.example.education.presentation.fragments_old.homework2.fourRectangles.FourRectanglesFragment

class   InputNumberFragment : Fragment(R.layout.fragment_input_number) {

    private val viewBinding: FragmentInputNumberBinding by viewBinding(FragmentInputNumberBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextWatchers()
        inputBiography()


        viewBinding.btnToRectangles.isEnabled = clicableBtnBio && clicableBtnNumb

        with(viewBinding) {
            btnToRectangles.setOnClickListener {
                (requireActivity() as? MainActivity)?.addFragment(
                    FourRectanglesFragment.getInstance(),
                    FourRectanglesFragment.FOUR_RECTANGLES_TAG,
                    detachCurrent = true
                )
            }
        }

    }

    private var clicableBtnNumb: Boolean = false
    private var clicableBtnBio: Boolean = false


    private fun inputBiography() {
        with(viewBinding) {
            biographyEt.addTextChangedListener(onTextChanged = { s: CharSequence?, _: Int, _: Int, count: Int ->
                s?.let {
                    viewBinding.btnToRectangles.isEnabled = count > 0 && clicableBtnNumb
                    clicableBtnBio = count > 0

                }
            })
        }
    }

    private fun initTextWatchers() {
        viewBinding.apply {
            numberEt.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    if ((numberEt.text?.length ?: 0) < 5) {
                        numberEt.setText("+7 (9")
                    }
                }
            }


            var beFull: String = ""

            numberEt.addTextChangedListener(

                onTextChanged = { input: CharSequence?, count: Int, start: Int, before: Int ->
                    input?.let { str ->
                        if (start > before) {
                            if (str.length < 18) {
                                viewBinding.btnToRectangles.isEnabled = false
                                clicableBtnNumb = false
                                viewBinding.biographyEt.isEnabled = false
                            }
                            when {
                                str.length < 5 -> {
                                    numberEt.setText("+7 (9")
                                    numberEt.setSelection(numberEt.text?.length ?: 5)
                                }

//                                str.length == 9 -> {
//                                    numberEt.setText(numberEt.text.toString()?: "+7 ")
//                                    numberEt.setSelection(7)
//                                } -- Не убирается скобка
                                else -> {
                                    // extra logic
                                    //+7 (999) 999-99-99
                                    //123456789012345678
                                }
                            }
                        }
                        if (before > start) {


                            val be: String = numberEt.text.toString()

                            when (str.length) {
                                7 -> {

                                    numberEt.setText(be.plus(") "))
                                    numberEt.setSelection(numberEt.text?.length ?: 9)
                                }

                                12 -> {
                                    numberEt.setText(be.plus("-"))
                                    numberEt.setSelection(numberEt.text?.length ?: 13)
                                }

                                15 -> {
                                    numberEt.setText(be.plus("-"))
                                    numberEt.setSelection(numberEt.text?.length ?: 16)
                                }

                                18 -> {
                                    beFull = be

                                }

                            }

                            if (str.length >= 18) {
                                numberEt.setText(beFull)
                                numberEt.setSelection(numberEt.text?.length ?: 18)
                                clicableBtnNumb = true
                                viewBinding.biographyEt.isEnabled = true
                                viewBinding.btnToRectangles.isEnabled = clicableBtnBio && true
                            } else {
                                clicableBtnNumb = false
                                viewBinding.biographyEt.isEnabled = false

                            }

                        }

                    }
                }
            )
        }
    }


    companion object {
        const val INPUT_NUMBER_FRAGMENT_TAG = "INPUT_NUMBER_FRAGMENT_TAG"

        fun getInstance() = InputNumberFragment()
    }
}