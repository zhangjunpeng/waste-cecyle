package com.nextmar.requestdata.repo

import com.nextmar.requestdata.RequestResult
import com.nextmar.requestdata.datasource.DataSource
import com.nextmar.requestdata.model.*;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Repository(private val dataSource: DataSource) {

    companion object {
        val instance: Repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Repository(DataSource())
        }
    }


    suspend fun login(username: String, password: String): RequestResult<Any> {
        return withContext(Dispatchers.IO){
            dataSource.numberLogin(username, password)
        }
    }


}