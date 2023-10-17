package com.l_george.clocker.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.l_george.clocker.data.models.AlarmModel

@Entity
data class AlarmEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val time: String,
    val actionId: Int,
    val isActive: Boolean
)

fun AlarmEntity.toModel(): AlarmModel = AlarmModel(id, time, actionId, isActive)
