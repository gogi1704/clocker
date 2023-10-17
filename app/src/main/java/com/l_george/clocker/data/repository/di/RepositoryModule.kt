package com.l_george.clocker.data.repository.di

import com.l_george.clocker.data.db.dao.AlarmDao
import com.l_george.clocker.data.repository.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(dao: AlarmDao): AlarmRepository = AlarmRepository(dao)
}