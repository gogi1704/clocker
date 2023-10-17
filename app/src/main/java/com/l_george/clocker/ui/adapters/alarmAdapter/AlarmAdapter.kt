package com.l_george.clocker.ui.adapters.alarmAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.l_george.clocker.R
import com.l_george.clocker.data.models.AlarmModel
import com.l_george.clocker.databinding.ItemAlarmLayoutBinding

interface AlarmListener {
    fun onOf(alarmModel: AlarmModel)
}

class AlarmAdapter(private val alarmListener: AlarmListener) :
    ListAdapter<AlarmModel, AlarmAdapter.AlarmViewHolder>(AlarmCallBack()) {

    class AlarmViewHolder(
        private val binding: ItemAlarmLayoutBinding,
        private val alarmListener: AlarmListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlarmModel) {
            binding.time.text = item.time
            binding.checkBox.setImageResource(if (item.isActive) R.drawable.switch_on else R.drawable.switch_off)
            binding.checkBox.setOnClickListener {
                alarmListener.onOf(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding =
            ItemAlarmLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmViewHolder(binding , alarmListener)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}