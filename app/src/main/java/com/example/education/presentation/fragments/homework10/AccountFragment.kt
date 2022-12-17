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
import com.example.education.data.bd.entity.User
import com.example.education.data.bd.local.DatabaseHandler
import com.example.education.data.bd.model.UserUpdateLogin
import com.example.education.databinding.FragmentAccountBinding
import com.example.education.presentation.MainActivity
import com.example.education.presentation.utils.SignInUserForApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountFragment : Fragment(R.layout.fragment_account) {

    var idUser = -1;

    private val viewBinding: FragmentAccountBinding
            by viewBinding(FragmentAccountBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
        idUser = pref?.getInt(SignInUserForApp.USER_ID_TAG, -1) ?: -1

        if (idUser == -1) {
            findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user: User? = null

        lifecycleScope.launch(Dispatchers.IO) {
            user = DatabaseHandler.roomDatabase?.getUserDao()?.findById(idUser)

            withContext(Dispatchers.Main) {
                viewBinding.loginEt.setText(user!!.login)
                viewBinding.btnEdit.isEnabled = true
            }
        }

        with(viewBinding) {
            btnEdit.setOnClickListener {
                val newLogin = loginEt.text.toString()

                if (newLogin == user?.login) {
                    Toast.makeText(context, "Change your login", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                lifecycleScope.launch(Dispatchers.IO) {
                    DatabaseHandler.roomDatabase?.getUserDao()
                        ?.updateUserLogin(UserUpdateLogin(newLogin, user!!.id))
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Login changed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            btnLogout.setOnClickListener {
                val pref =
                    activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
                with(pref.edit()) {
                    putInt(SignInUserForApp.USER_ID_TAG, -1)
                    apply()
                }
                findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
                (requireActivity() as? MainActivity)?.changeBottomNavigationVisibility(true)
            }
        }
    }
}
