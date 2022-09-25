package com.example.education.fragments.inputNumber

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.MainActivity
import com.example.education.R
import com.example.education.databinding.FragmentInputNumberBinding
import com.example.education.fragments.fourRectangles.FourRectanglesFragment

class InputNumberFragment : Fragment(R.layout.fragment_input_number) {

    private val viewBinding: FragmentInputNumberBinding by viewBinding(FragmentInputNumberBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputNumberPhone()
        inputBiography()

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

    private fun inputBiography() {
        with(viewBinding) {
            biographyEt.addTextChangedListener(onTextChanged = { s: CharSequence?, start: Int, before: Int, count: Int ->
                s?.let {
                    btnToRectangles.isEnabled = count > 0
                }
            })
        }
    }

    private fun inputNumberPhone() {
        with(viewBinding) {
            numberEt.addTextChangedListener(object : TextWatcher {
                private var mSelfChange = false

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s == null || mSelfChange) {
                        return
                    }

                    val preparedStr = s.replace(Regex("(\\D*)"), "")
                    var resultStr = ""
                    for (i in preparedStr.indices) {
                        resultStr = when (i) {
                            0 -> resultStr.plus("+7 (9")
                            2 -> resultStr.plus(preparedStr[i])
                            3 -> resultStr.plus(preparedStr[i])
                            4 -> resultStr.plus(") ".plus(preparedStr[i]))
                            5 -> resultStr.plus(preparedStr[i])
                            6 -> resultStr.plus(preparedStr[i])
                            7 -> resultStr.plus("-".plus(preparedStr[i]))
                            8 -> resultStr.plus(preparedStr[i])
                            9 -> resultStr.plus("-".plus(preparedStr[i]))
                            10 -> resultStr.plus(preparedStr[i])
                            else -> resultStr
                        }
                    }

                    viewBinding.biographyEt.isEnabled = resultStr.length == 18

                    mSelfChange = true
                    val oldSelectionPos = numberEt.selectionStart
                    numberEt.setText(resultStr)
                    if (numberEt.selectionStart != numberEt.length()) {
                        numberEt.setSelection(if (oldSelectionPos > resultStr.length) resultStr.length else oldSelectionPos)
                    } else {
                        numberEt.setSelection(resultStr.length)
                    }
                    mSelfChange = false
                }

                override fun afterTextChanged(s: Editable?) {}
            })

        }


    }


    companion object {
        const val INPUT_NUMBER_FRAGMENT_TAG = "INPUT_NUMBER_FRAGMENT_TAG"

        fun getInstance() = InputNumberFragment()
    }
}