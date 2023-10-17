package com.l_george.clocker.data.db.di

import android.content.Context
import androidx.room.Room
import com.l_george.clocker.data.db.AlarmDataBase
import com.l_george.clocker.data.db.dao.AlarmDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    companion object {
        private const val DATA_BASE_NAME = "ALARM_DB"
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AlarmDataBase =
        Room.databaseBuilder(context, AlarmDataBase::class.java, DATA_BASE_NAME).build()

    @Provides
    @Singleton
    fun provideDao(db: AlarmDataBase): AlarmDao = db.alarmDao()
}