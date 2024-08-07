package com.plcoding.coroutinesmasterclass.sections.coroutine_basics

import com.plcoding.coroutinesmasterclass.util.db.PeopleDao
import com.plcoding.coroutinesmasterclass.util.db.Person
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.delay

suspend fun fetchData(
    peopleDao: PeopleDao,
    client: HttpClient
) {
    val response = client.get("/people/1")
    peopleDao.insertPerson(Person(name = "John Doe"))
}