package com.l_george.clocker.ui.adapters.alarmAdapter

import androidx.recyclerview.widget.DiffUtil
import com.l_george.clocker.data.models.AlarmModel

class AlarmCallBack : DiffUtil.ItemCallback<AlarmModel>() {
    override fun areItemsTheSame(oldItem: AlarmModel, newItem: AlarmModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AlarmModel, newItem: AlarmModel): Boolean =
        oldItem == newItem
}