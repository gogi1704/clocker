package com.l_george.clocker.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.l_george.clocker.data.models.WorldTimeModel

class WorldCallBack:DiffUtil.ItemCallback<WorldTimeModel>() {
    override fun areItemsTheSame(oldItem: WorldTimeModel, newItem: WorldTimeModel): Boolean = oldItem.city == newItem.city

    override fun areContentsTheSame(oldItem: WorldTimeModel, newItem: WorldTimeModel): Boolean = oldItem == newItem
}