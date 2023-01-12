package com.example.education.presentation.fragments.homework10

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.data.bd.entity.Settings
import com.example.education.data.bd.entity.User
import com.example.education.data.bd.local.DatabaseHandler
import com.example.education.databinding.FragmentRegistrationBinding
import com.example.education.presentation.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val viewBinding: FragmentRegistrationBinding
            by viewBinding(FragmentRegistrationBinding::bind)


    override fun onStart() {
        super.onStart()
        (requireActivity() as? MainActivity)?.changeBottomNavigationVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            btnToLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }

            btnRegister.setOnClickListener {
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
                    val isAddUser = withContext(Dispatchers.IO) {
                        try {
                            DatabaseHandler.roomDatabase?.getUserDao()
                                ?.addUser(User(login, password))
                            Log.d("TEST TAG", "Add new User")
                            withContext(Dispatchers.IO) {
                                val user = DatabaseHandler.roomDatabase?.getUserDao()
                                    ?.findByLoginAndPassword(login, password)
                                DatabaseHandler.roomDatabase?.getSettingsDao()
                                    ?.addSettingsUser(Settings(user!!.id,
                                        settings1 = false,
                                        settings2 = false,
                                        settings3 = false))
                            }
                            true
                        } catch (ex: SQLiteConstraintException) {
                            false
                        }
                    }

                    if (!isAddUser) {
                        Toast.makeText(context,
                            "Login is already in USE!",
                            Toast.LENGTH_SHORT)
                            .show()
                        return@launch
                    }

                    findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    return@launch

                }
            }
        }
    }
}
