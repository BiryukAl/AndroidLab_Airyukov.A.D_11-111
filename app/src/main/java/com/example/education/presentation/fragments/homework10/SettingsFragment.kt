package com.example.education.presentation.fragments.homework10

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.data.bd.entity.Settings
import com.example.education.data.bd.local.DatabaseHandler
import com.example.education.databinding.FragmentSettingsBinding
import com.example.education.presentation.utils.SignInUserForApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewBinding: FragmentSettingsBinding
            by viewBinding(FragmentSettingsBinding::bind)

    var idUser = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
        idUser = pref?.getInt(SignInUserForApp.USER_ID_TAG, -1) ?: -1

        //Стрелки такой нет Но и ситуации такой не может
        //Непротестировано !!!
        if (idUser == -1) {
            findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val settings = DatabaseHandler.roomDatabase?.getSettingsDao()?.findById(idUser)

            withContext(Dispatchers.Main) {
                with(viewBinding) {
                    checkboxSetting1.isChecked = settings?.settings1!!
                    checkboxSetting2.isChecked = settings?.settings2!!
                    checkboxSetting3.isChecked = settings?.settings3!!
                }
            }
        }

        with(viewBinding) {
            btnSaveSettings.setOnClickListener {
                val newSettings = Settings(
                    idUser,
                    checkboxSetting1.isChecked,
                    checkboxSetting2.isChecked,
                    checkboxSetting3.isChecked,
                )
                lifecycleScope.launch(Dispatchers.IO) {
                    DatabaseHandler.roomDatabase?.getSettingsDao()?.updateUserSettings(newSettings)
                }
            }
        }
    }
}
