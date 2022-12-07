package com.example.education.presentation.fragments.homework8

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.education.R
import com.example.education.databinding.FragmentCreateForegroundServiceBinding
import com.example.education.presentation.service.MyForegroundService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateForegroundServiceFragment : Fragment(R.layout.fragment_create_foreground_service) {

    private val viewBinding: FragmentCreateForegroundServiceBinding
            by viewBinding(FragmentCreateForegroundServiceBinding::bind)

    private var serviceBinder: MyForegroundService.MainBinder? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceBinder = service as? MyForegroundService.MainBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceBinder = null
        }

    }
    val url = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fproject-seo.net%2Fwp-content%2Fuploads%2F2019%2F12%2FURL.png&imgrefurl=https%3A%2F%2Fproject-seo.net%2Fterms-ru%2Furl%2F&tbnid=iHEYBt69d7M4eM&vet=12ahUKEwjwg43UwOj7AhXEGHcKHVBlD9MQMygAegUIARC6AQ..i&docid=29TZsjme4w7Z9M&w=1429&h=766&q=url%20%D0%BD%D0%B0%20%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D1%83&client=ubuntu&ved=2ahUKEwjwg43UwOj7AhXEGHcKHVBlD9MQMygAegUIARC6AQ"
    var bitmap: Bitmap? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        with(viewBinding) {
            btn1.setOnClickListener {
                val intent = Intent(activity, MyForegroundService::class.java)
                activity?.startForegroundService(intent)
            }

            btn2.setOnClickListener {
                lifecycleScope.launch { downloadImg() }
                Log.d("MyGlide", "DownLoad")
                serviceBinder?.setImage(bitmap, url)
            }

            btn3.setOnClickListener {
                Glide.with(this@CreateForegroundServiceFragment)
                    .load(url)
                    .centerCrop()
                    .into(viewBinding.imageView)


//                imageView.setImageBitmap(bitmap)
            }

            btn4.setOnClickListener {
                val intent = Intent(activity, MyForegroundService::class.java)
                activity?.stopService(intent)
            }
        }


    }

    private suspend fun downloadImg() {
        bitmap = withContext(Dispatchers.IO) {
            Glide.with(this@CreateForegroundServiceFragment)
                .asBitmap()
                .load(url)
                .submit()
                .get()
        }
    }

    companion object {
        const val CREATE_FOREGROUND_SERVICE_FRAGMENT_TAG = "CREATE_FOREGROUND_SERVICE_FRAGMENT_TAG"

        fun getInstance() = CreateForegroundServiceFragment()

    }
}