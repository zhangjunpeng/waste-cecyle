package com.nextmar.requestdata.repo

import com.nextmar.requestdata.Result
import com.nextmar.requestdata.datasource.DataSource
import com.nextmar.requestdata.model.*;


class Repository(private val dataSource: DataSource) {

    companion object {
        val instance: Repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Repository(DataSource())
        }
    }



    fun login(username: String, password: String): Result<NumberLoginData> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
//            setLoggedInUser(result.data)
        }

        return result
    }


}