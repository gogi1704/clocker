package com.l_george.clocker.data.repository

import com.l_george.clocker.data.db.dao.AlarmDao
import com.l_george.clocker.data.db.entities.AlarmEntity
import com.l_george.clocker.data.models.AlarmModel
import com.l_george.clocker.data.models.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepository @Inject constructor(private val dao: AlarmDao) {

    fun getAll(): Flow<List<AlarmEntity>> = dao.getAll()

    suspend fun insertAlarm(alarm: AlarmModel) = dao.insertAlarm(alarm.toEntity())


    suspend fun getById(id:Int) = dao.getByID(id)

    suspend fun remove(id:Int) = dao.removeById(id)
}