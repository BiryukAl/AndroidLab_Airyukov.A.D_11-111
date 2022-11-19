package com.example.education.presentation.fragments.homework6

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock.elapsedRealtime
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentCreateNotifyBinding
import com.example.education.presentation.utils.NotificationsHandler
import com.example.education.presentation.utils.SampleBroadcastReceiver

class CreateNotifyFragment : Fragment(R.layout.fragment_create_notify) {

    private val viewBinding: FragmentCreateNotifyBinding
            by viewBinding(FragmentCreateNotifyBinding::bind)

    private var notificationHandler: NotificationsHandler? = null
    private var alarmManager: AlarmManager? = null

    private val optionNotify: Array<String> = arrayOf("", "", "", "") // Можно использовать DataClass
    private var optionTime: Long = 0L


    private var etHandlerIsEmpty: Boolean = false
    private var etMessageIsEmpty: Boolean = false
    private var etTimeOutIsEmpty: Boolean = false
    private var etBigTextIsEmpty: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationHandler = NotificationsHandler(context = requireContext())

        with(viewBinding) {


            checkBox.setOnClickListener {
                etBigText.isEnabled = !etBigText.isEnabled
            }

            etHandler.addTextChangedListener(onTextChanged = { s: CharSequence?, _: Int, _: Int, count: Int ->
                s?.let {
                    etHandlerIsEmpty = count > 0
                    editTextIsEmpty()
                }

            })
            etMessage.addTextChangedListener(onTextChanged = { s: CharSequence?, _: Int, _: Int, count: Int ->
                s?.let {
                    etMessageIsEmpty = count > 0
                    editTextIsEmpty()
                }
            })
            etTimeout.addTextChangedListener(onTextChanged = { s: CharSequence?, _: Int, _: Int, count: Int ->
                s?.let {
                    etTimeOutIsEmpty = count > 0
                    editTextIsEmpty()
                }
            })
            etBigText.addTextChangedListener(onTextChanged = { s: CharSequence?, _: Int, _: Int, count: Int ->
                s?.let {
                    etBigTextIsEmpty = count > 0
                    editTextIsEmpty()
                }
            })




            btnShowNotification.setOnClickListener {
                val handler = viewBinding.etHandler.text.toString()
                val message = viewBinding.etMessage.text.toString()
                val bigText = viewBinding.etBigText.text.toString()
                val timeOut = Integer.valueOf(viewBinding.etTimeout.text.toString())

                optionNotify[0] = handler
                optionNotify[1] = message
                optionNotify[2] = bigText
                optionTime = elapsedRealtime() + timeOut.toLong()

                createAlarm(handler, message, bigText, (timeOut * 1000).toLong())
            }

            btnRevokeNotification.setOnClickListener {
                val timeOut = if (optionTime != 0L) {
                     elapsedRealtime() -  optionTime
                } else {
                    0L
                }
                if (timeOut > 0L) {

                    alarmManager?.cancel(Intent(context,
                        SampleBroadcastReceiver::class.java).apply {

                        putExtra(SampleBroadcastReceiver.HEADER_TEXT, optionNotify[0])
                        putExtra(SampleBroadcastReceiver.MESSAGE_TEXT, optionNotify[1])
                        putExtra(SampleBroadcastReceiver.BIG_TEXT, optionNotify[2])

                    }.let { intent ->
                        PendingIntent.getBroadcast(context,
                            0,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT)
                    })

                }
            }

            btnShowTimeLastRestart.setOnClickListener {

                Toast.makeText(context,
                    "Time last restart: " + elapsedRealtime().toString(),
                    Toast.LENGTH_SHORT).show()

            }
        }


    }

    private fun createAlarm(
        headerText: String?,
        messageText: String?,
        bigText: String?,
        afterTime: Long,
    ) {
        alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        val time = elapsedRealtime() + afterTime

        val pendingIntent = Intent(context, SampleBroadcastReceiver::class.java).apply {
            putExtra(SampleBroadcastReceiver.HEADER_TEXT, headerText)
            putExtra(SampleBroadcastReceiver.MESSAGE_TEXT, messageText)
            putExtra(SampleBroadcastReceiver.BIG_TEXT, bigText)
        }.let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        alarmManager?.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, pendingIntent)

    }

    fun editTextIsEmpty() {
        if (!viewBinding.etBigText.isEnabled) {
            viewBinding.btnShowNotification.isEnabled =
                etHandlerIsEmpty && etTimeOutIsEmpty && etMessageIsEmpty
        } else {
            viewBinding.btnShowNotification.isEnabled =
                etHandlerIsEmpty && etTimeOutIsEmpty && etMessageIsEmpty && etBigTextIsEmpty

        }
    }

    override fun onDestroy() {
        notificationHandler = null
        super.onDestroy()
    }

    companion object {
        const val CREATE_NOTIFY_FRAGMENT_TAG = "CREATE_NOTIFY_FRAGMENT_TAG"

        fun getInstance() = CreateNotifyFragment()

    }
}

