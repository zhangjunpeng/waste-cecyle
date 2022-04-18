package com.nextmar.requestdata.datasource

import android.text.TextUtils
import com.blankj.utilcode.util.LogUtils
import com.nextmar.requestdata.GsonUtils
import com.nextmar.requestdata.NameSpace
import com.nextmar.requestdata.RESTURL
import com.nextmar.requestdata.Result
import com.nextmar.requestdata.model.*


import okhttp3.*
import java.util.concurrent.TimeUnit


class DataSource() {

    val errorMsg = ResultModel(false, "", "服务器异常")


    val client = OkHttpClient.Builder().build()


    fun getResponse(paramMap: Map<String, String>, url: String): Response {
        val client = OkHttpClient.Builder()

//            .addInterceptor(LoggingInterceptor())
            .build()
        val formBodyBuilder = FormBody.Builder()
        for (item in paramMap.keys) {
            formBodyBuilder.add(item, paramMap[item].toString())
        }
        val request: Request = Request.Builder().url(url).post(formBodyBuilder.build()).build()
        val call: Call = client.newCall(request)
        return call.execute()
    }

    fun login(username: String, password: String): Result<NumberLoginData> {
        try {
            val formBody =
                FormBody.Builder().add("account", username).add("password", password).build()
            val request: Request = Request.Builder().url(RESTURL.NormalLogin).post(formBody).build()
            val call: Call = client.newCall(request)
            val response = call.execute()
            val dataStr = response.body!!.string()
            LogUtils.i(dataStr)
            if (response.isSuccessful) {

                val model = GsonUtils.fromJson(dataStr, ResultModel::class.java)
                if (model.res!!) {
//                    SPUtils.getInstance().put(NameSpace.IsLogin, true)
//                    SPUtils.getInstance().put(NameSpace.Token, loggedInUser.data.api_key)
                }
                return Result.Success(model.data as NumberLoginData)
            } else {
                return Result.Error(
                    GsonUtils.fromJson(
                        dataStr, ResultModel::class.java
                    )
                )
            }
        } catch (e: java.lang.Exception) {
            return Result.Error(errorMsg)
        }
    }


}