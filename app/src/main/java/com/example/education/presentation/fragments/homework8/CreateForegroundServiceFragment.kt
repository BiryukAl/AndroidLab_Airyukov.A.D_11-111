package com.example.education.presentation.fragments.homework8

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.education.R
import com.example.education.databinding.FragmentCreateForegroundServiceBinding
import com.example.education.presentation.service.MyForegroundService

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

    private var isStartedService = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.bindService(
            Intent(activity, MyForegroundService::class.java),
            connection,
            Service.BIND_AUTO_CREATE
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            btn1.setOnClickListener {
                val intent = Intent(activity, MyForegroundService::class.java)
                activity?.startForegroundService(intent)
                isStartedService = true
            }

            btn2.setOnClickListener {
                serviceBinder?.downloadBind()
            }

            btn3.setOnClickListener {
                if (isStartedService && (serviceBinder?.isDownload() == true)) {
                    Glide.with(this@CreateForegroundServiceFragment)
                        .load("https://s1.1zoom.ru/b6755/973/Cats_Kittens_Grey_Glance_Wicker_basket_517968_2048x1152.jpg")
                        .centerCrop()
                        .into(imageView)
                } else {
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                }

            }

            btn4.setOnClickListener {
                serviceBinder?.stopService()

//               Это не убирает уведомление, почему то???
                /*val intent = Intent(activity, MyForegroundService::class.java).apply {
                    extras?.getString(MyForegroundService.SERVICE_ACTION_TAG,
                        MyForegroundService.STOP)
                }
                activity?.stopService(intent)*/


            }
        }
    }

    override fun onDestroy() {
        activity?.unbindService(connection)
        super.onDestroy()
    }

    companion object {
        const val CREATE_FOREGROUND_SERVICE_FRAGMENT_TAG = "CREATE_FOREGROUND_SERVICE_FRAGMENT_TAG"

        fun getInstance() = CreateForegroundServiceFragment()

    }
}