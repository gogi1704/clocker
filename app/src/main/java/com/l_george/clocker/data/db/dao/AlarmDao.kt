package com.l_george.clocker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.l_george.clocker.data.db.entities.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM AlarmEntity")
    fun getAll(): Flow<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmEntity)

    @Query("SELECT * FROM AlarmEntity WHERE id = :id")
    suspend fun getByID(id: Int): AlarmEntity

    @Query("DELETE FROM AlarmEntity WHERE id =:id")
    suspend fun removeById(id:Int)

}