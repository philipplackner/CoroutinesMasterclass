package com.plcoding.coroutinesmasterclass.util.db

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class Task(
    val title: String,
    val dateTime: Long,
    val isDone: Boolean,
    @PrimaryKey val id: Int = 0
)

@Dao
interface TaskDao {

    @Query("""
        SELECT *
        FROM task
        WHERE :startMillis <= dateTime AND :endMillis > dateTime
    """)
    fun getTasksBetween(startMillis: Long, endMillis: Long): Flow<List<Task>>
}