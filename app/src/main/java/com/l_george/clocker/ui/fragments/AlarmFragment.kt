package com.l_george.clocker.ui.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.l_george.clocker.R
import com.l_george.clocker.data.models.AlarmModel
import com.l_george.clocker.databinding.FragmentAlarmBinding
import com.l_george.clocker.receiver.ClockReceiver
import com.l_george.clocker.ui.adapters.alarmAdapter.AlarmAdapter
import com.l_george.clocker.ui.adapters.alarmAdapter.AlarmListener
import com.l_george.clocker.ui.viewModels.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmBinding
    private val viewModel: AlarmViewModel by viewModels()
    private lateinit var adapter: AlarmAdapter
    private lateinit var timePicker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private var alarmsListSize = 0 // TODO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmBinding.inflate(layoutInflater, container, false)
        adapter = AlarmAdapter(object : AlarmListener {
            override fun onOf(alarmModel: AlarmModel) {
                viewModel.insertAlarm(alarmModel.copy(isActive = !alarmModel.isActive))
            }
        })
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set time")
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .build()

        with(binding) {
            createAlarm.setOnClickListener {
                timePicker.show(requireActivity().supportFragmentManager, "tag")
            }
            recyclerAlarm.adapter = adapter

            viewModel.alarmsLiveData.observe(viewLifecycleOwner) {
                alarmsListSize = it.size + 1
                adapter.submitList(it.sortedBy { alarm -> alarm.time })
            }


        }

        timePicker.addOnPositiveButtonClickListener {

            if (!Settings.canDrawOverlays(
                    requireActivity()
                )
            ) {
                Toast.makeText(
                    requireContext(),
                    "Permission is required to use windows on top of other applications.",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + requireActivity().packageName)
                )
                startActivity(intent)
            } else {
                var chooseHours = timePicker.hour
                var chooseMinutes = timePicker.minute

                val alarmTime = Calendar.getInstance()
                alarmTime.apply {
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    set(Calendar.HOUR, timePicker.hour)
                    set(Calendar.MINUTE, timePicker.minute)
                }
                if (Calendar.getInstance().after(alarmTime)) {
                    alarmTime
                        .add(Calendar.DATE, 1)
                }
                val alarmClockInfo =
                    AlarmManager.AlarmClockInfo(
                        alarmTime.timeInMillis,
                        getAlarmInfoPendingIntent()
                    )

                val alarmIntent = getAlarmActionIntent()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                    if (alarmManager.canScheduleExactAlarms()) {

                        alarmManager.setAlarmClock(alarmClockInfo, alarmIntent)
                        val hours =
                            if (chooseHours.toString().length ==1) "0${chooseHours}"
                            else chooseHours.toString()
                        val min =
                            if (chooseMinutes.toString().length ==1) "0${chooseMinutes}"
                            else chooseMinutes.toString()
                        viewModel.insertAlarm(
                            AlarmModel(
                                id = alarmsListSize,
                                time = "$hours:$min",
                                actionId = alarmsListSize,
                                isActive = true
                            )
                        )

                    } else {
                        Intent().also { intent ->
                            intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                            context?.startActivity(intent)
                        }
                    }

                } else {
                    alarmManager.setAlarmClock(alarmClockInfo, alarmIntent)
                    viewModel.insertAlarm(
                        AlarmModel(
                            id = alarmsListSize,
                            time = "$chooseHours $chooseMinutes",
                            actionId = alarmsListSize,
                            isActive = true
                        )
                    )
                }
            }


        }



        requireActivity().findViewById<ImageView>(R.id.home).setImageResource(R.drawable.home_off)
        requireActivity().findViewById<ImageView>(R.id.world).setImageResource(R.drawable.world_off)
        requireActivity().findViewById<ImageView>(R.id.alarm).setImageResource(R.drawable.alarm_on)
        return binding.root
    }


    private fun getAlarmActionIntent(): PendingIntent {
        val intent = Intent(requireContext(), ClockReceiver::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = alarmsListSize.toString()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                requireContext(),
                alarmsListSize,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

            )
        } else {
            PendingIntent.getBroadcast(
                requireContext(),
                alarmsListSize,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

    }

    private fun getAlarmInfoPendingIntent(): PendingIntent {
        val alarmIntent = Intent(requireContext(), ClockReceiver::class.java)
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        alarmIntent.action = alarmsListSize.toString()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                requireContext(),
                alarmsListSize,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

            )
        } else {
            PendingIntent.getBroadcast(
                requireContext(),
                alarmsListSize,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

}