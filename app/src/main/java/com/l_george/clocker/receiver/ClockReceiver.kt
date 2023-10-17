package com.l_george.clocker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Looper
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.l_george.clocker.R
import com.l_george.clocker.data.db.entities.toModel
import com.l_george.clocker.data.repository.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ClockReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repository: AlarmRepository
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var ringtone: Ringtone



    override fun onReceive(context: Context?, intent: Intent?) {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, uri)
        coroutineScope.launch {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }
            val model = intent?.action?.toInt()?.let { repository.getById(it) }?.toModel()

            if (model != null) {
                if (model.isActive) {
                    val windowManager =
                        context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        WindowManager.LayoutParams(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT
                        )
                    } else {
                        WindowManager.LayoutParams(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.TYPE_PHONE,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT
                        )
                    }

                    val inflater =
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val view = inflater.inflate(R.layout.alarm_layout, null)

                    val button = view.findViewById<Button>(R.id.button_stop_alarm)
                    val textHours = view.findViewById<TextView>(R.id.text_hours_window)

                    textHours.text = model.time

                    button.setOnClickListener{
                        windowManager.removeView(view)
                        ringtone.stop()

                    }

                    windowManager.addView(view, params)
                    ringtone.play()
                    repository.remove(model.id)

                } else {
                    repository.remove(model.id)
                }


            }
        }
        intent?.action
    }
}