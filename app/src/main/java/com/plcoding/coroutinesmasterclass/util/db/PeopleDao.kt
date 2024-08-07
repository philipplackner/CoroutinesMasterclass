package com.plcoding.coroutinesmasterclass.util.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PeopleDao {

    @Insert
    suspend fun insertPerson(person: Person)

    @Query("SELECT * FROM person")
    suspend fun getPeople(): List<Person>
}