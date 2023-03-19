package com.example.education.presentation.fragments_old.homework6

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
import com.example.education.presentation.model.NotificationUnit
import com.example.education.presentation.utils.NotificationsHandler
import com.example.education.presentation.utils.SampleBroadcastReceiver

class CreateNotifyFragment : Fragment(R.layout.fragment_create_notify) {

    private val viewBinding: FragmentCreateNotifyBinding
            by viewBinding(FragmentCreateNotifyBinding::bind)

    private var notificationHandler: NotificationsHandler? = null
    private var alarmManager: AlarmManager? = null

    private val dataNotify = NotificationUnit()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationHandler = NotificationsHandler(context = requireContext())

        with(viewBinding) {

            checkBox.setOnCheckedChangeListener { _, isCheck ->
                etBigText.isEnabled = isCheck
            }

            etHandler.addTextChangedListener(onTextChanged = { s: CharSequence?, _: Int, _: Int, count: Int ->
                s?.let {
                    dataNotify.headerIsNotEmpty = s.isNotEmpty()
                    allEditTextNotIsEmpty()
                }

            })
            etMessage.addTextChangedListener(onTextChanged = { s: CharSequence?, _: Int, _: Int, count: Int ->
                s?.let {
                    dataNotify.messageIsNotEmpty = s.isNotEmpty()
                    allEditTextNotIsEmpty()
                }
            })
            etTimeout.addTextChangedListener(onTextChanged = { s: CharSequence?, _: Int, _: Int, count: Int ->
                s?.let {
                    dataNotify.timeOutIsNotEmpty = s.isNotEmpty()
                    allEditTextNotIsEmpty()
                }
            })
            etBigText.addTextChangedListener(onTextChanged = { s: CharSequence?, _: Int, _: Int, count: Int ->
                s?.let {
                    dataNotify.bigTextIsNotEmpty = s.isNotEmpty()
                    allEditTextNotIsEmpty()
                }
            })


            btnShowNotification.setOnClickListener {
                with(dataNotify) {
                    headerText = viewBinding.etHandler.text.toString()
                    messageText = viewBinding.etMessage.text.toString()
                    timeOut = (Integer.valueOf(viewBinding.etTimeout.text.toString())).toLong()
                    bigText = viewBinding.etBigText.text.toString()
                }

                createAlarm(headerText = dataNotify.headerText,
                    messageText = dataNotify.messageText,
                    bigText = if (viewBinding.etBigText.isEnabled) {
                        dataNotify.bigText
                    } else {
                        null
                    },
                    afterTime = if (dataNotify.timeOut == 0L) {
                        0L
                    } else {
                        dataNotify.timeOut * 1000
                    }
                )
            }

            btnRevokeNotification.setOnClickListener {
                val timeOut = if (dataNotify.timeOut != 0L) {
                    elapsedRealtime() - dataNotify.timeOut
                } else {
                    0L
                }
                if (timeOut > 0L) {

                    alarmManager?.cancel(Intent(context,
                        SampleBroadcastReceiver::class.java).apply {

                        putExtra(SampleBroadcastReceiver.HEADER_TEXT,
                            dataNotify.headerText)
                        putExtra(SampleBroadcastReceiver.MESSAGE_TEXT,
                            dataNotify.messageText)
                        putExtra(SampleBroadcastReceiver.BIG_TEXT, dataNotify.bigText)

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

    private fun allEditTextNotIsEmpty() {
        val out = dataNotify.headerIsNotEmpty &&
                dataNotify.messageIsNotEmpty && dataNotify.timeOutIsNotEmpty

        if (!viewBinding.etBigText.isEnabled) {
            viewBinding.btnShowNotification.isEnabled = out
        } else {
            viewBinding.btnShowNotification.isEnabled =
                out && dataNotify.bigTextIsNotEmpty
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
