package com.example.education.presentation.fragments.homework10

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.data.bd.local.DatabaseHandler
import com.example.education.databinding.FragmentLoginBinding
import com.example.education.presentation.MainActivity
import com.example.education.presentation.utils.SignInUserForApp.Companion.USER_ID_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewBinding: FragmentLoginBinding
            by viewBinding(FragmentLoginBinding::bind)

    override fun onStart() {
        super.onStart()
        (requireActivity() as? MainActivity)?.changeBottomNavigationVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            btnToRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }

            btnLogin.setOnClickListener {
                val login = loginEt.text.toString()
                val password = passwordEt.text.toString()

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context,
                        "Empty input",
                        Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    val user = withContext(Dispatchers.IO) {
                        DatabaseHandler.roomDatabase?.getUserDao()
                            ?.findByLoginAndPassword(login, password)
                    }

                    if (user == null) {
                        Toast.makeText(context,
                            "Incorrect login and/or password",
                            Toast.LENGTH_SHORT)
                            .show()
                        return@launch
                    }
                    val idUser = user.id
                    val pref =
                        activity?.getPreferences(Context.MODE_PRIVATE) ?: return@launch
                    with(pref.edit()) {
                        putInt(USER_ID_TAG, idUser)
                        commit()
                    }
                    (requireActivity() as? MainActivity)?.changeBottomNavigationVisibility(true)
                    findNavController().navigate(R.id.action_loginFragment_to_accountFragment)

                }
            }
        }
    }
}
