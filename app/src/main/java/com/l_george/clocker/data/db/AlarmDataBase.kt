package com.l_george.clocker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.l_george.clocker.data.db.dao.AlarmDao
import com.l_george.clocker.data.db.entities.AlarmEntity

@Database([AlarmEntity::class], version = 1)
abstract class AlarmDataBase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}