package com.l_george.clocker.data.models

import com.l_george.clocker.data.db.entities.AlarmEntity

data class AlarmModel(
    val id: Int ,
    val time: String,
    val actionId: Int,
    val isActive: Boolean
)
fun AlarmModel.toEntity(): AlarmEntity = AlarmEntity(id, time, actionId, isActive)
