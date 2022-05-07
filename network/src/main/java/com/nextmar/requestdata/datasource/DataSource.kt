package com.nextmar.requestdata.datasource

import com.blankj.utilcode.util.LogUtils
import com.nextmar.requestdata.RESTURL
import com.nextmar.requestdata.RequestResult
import com.nextmar.requestdata.model.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


import okhttp3.*


class DataSource() : DataSourceInterface {

    companion object {
        val instance: DataSource by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DataSource()
        }
    }

    val errorMsg = ResultModel<Nothing>(false, "", "服务器异常")


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


    override fun numberLogin(username: String, password: String): RequestResult<LoginData> {

        try {
            val formBody =
                FormBody.Builder().add("phone", username).add("password", password).build()
            val request: Request = Request.Builder().url(RESTURL.NormalLogin).post(formBody).build()
            val call: Call = client.newCall(request)
            val response = call.execute()
            val dataStr = response.body!!.string()
            LogUtils.i(dataStr)
            if (response.isSuccessful) {
                val model = Json.decodeFromString<ResultModel<LoginData>>(dataStr)
                if (model.res!!) {
//                    SPUtils.getInstance().put(NameSpace.IsLogin, true)
//                    SPUtils.getInstance().put(NameSpace.Token, loggedInUser.data.api_key)
                    return RequestResult.Success(model.data as LoginData)

                } else {
                    val error = Json.decodeFromString<ResultModel<Nothing>>(dataStr)
                    return RequestResult.Error(
                        error
                    )
                }

            } else {
                val model = Json.decodeFromString<ResultModel<Nothing>>(dataStr)
                return RequestResult.Error(
                    model
                )
            }
        } catch (e: java.lang.Exception) {
            return RequestResult.Error(errorMsg)
        }
    }

    override fun scanLogin(code: String): RequestResult<LoginData> {
        try {
            val formBody =
                FormBody.Builder().add("code", code).build()
            val request: Request = Request.Builder().url(RESTURL.ScanLogin).post(formBody).build()
            val call: Call = client.newCall(request)
            val response = call.execute()
            val dataStr = response.body!!.string()
            LogUtils.i(dataStr)
            if (response.isSuccessful) {
                val model = Json.decodeFromString<ResultModel<LoginData>>(dataStr)
                if (model.res!!) {
//                    SPUtils.getInstance().put(NameSpace.IsLogin, true)
//                    SPUtils.getInstance().put(NameSpace.Token, loggedInUser.data.api_key)
                    return RequestResult.Success(model.data as LoginData)

                } else {
                    val error = Json.decodeFromString<ResultModel<Nothing>>(dataStr)
                    return RequestResult.Error(
                        error
                    )
                }

            } else {
                val model = Json.decodeFromString<ResultModel<Nothing>>(dataStr)
                return RequestResult.Error(
                    model
                )
            }
        } catch (e: java.lang.Exception) {
            return RequestResult.Error(errorMsg)
        }

    }


    override fun memberShowInfo(token: String, id: String): RequestResult<MemberShowData> {
        try {
            val formBody =
                FormBody.Builder().add("id", id).build()
            val request: Request =
                Request.Builder().url(RESTURL.NormalLogin).header("Token", token).post(formBody)
                    .build()
            val call: Call = client.newCall(request)
            val response = call.execute()
            val dataStr = response.body!!.string()
            LogUtils.i(dataStr)
            if (response.isSuccessful) {
                val model = Json.decodeFromString<ResultModel<LoginData>>(dataStr)
                return if (model.res!!) {
                    RequestResult.Success(model.data as MemberShowData)

                } else {
                    val error = Json.decodeFromString<ResultModel<Nothing>>(dataStr)
                    RequestResult.Error(
                        error
                    )
                }

            } else {
                val model = Json.decodeFromString<ResultModel<Nothing>>(dataStr)
                return RequestResult.Error(
                    model
                )
            }
        } catch (e: java.lang.Exception) {
            return RequestResult.Error(errorMsg)
        }
    }

    override fun rooShowInfo(id: String): RequestResult<RoomShowData> {
        TODO("Not yet implemented")
    }

    override fun carTotal(
        token: String,
        projectId: String,
        roomId: String?
    ): RequestResult<CarTotalData> {
        try {
            val url = StringBuffer(RESTURL.CarTotal)
            url.append("?project_id=$projectId")
            if (roomId!=null){
                url.append("&room_id=$roomId")
            }

            val request: Request =
                Request.Builder().url(url.toString()).header("Token", token).get().build()
            val call: Call = client.newCall(request)
            val response = call.execute()
            val dataStr = response.body!!.string()
            LogUtils.i(dataStr)
            if (response.isSuccessful) {
                val model = Json.decodeFromString<ResultModel<CarTotalData>>(dataStr)
                return if (model.res!!) {
                    RequestResult.Success(model.data as CarTotalData)

                } else {
                    val error = Json.decodeFromString<ResultModel<Nothing>>(dataStr)
                    RequestResult.Error(
                        error
                    )
                }

            } else {
                val model = Json.decodeFromString<ResultModel<Nothing>>(dataStr)
                return RequestResult.Error(
                    model
                )
            }
        } catch (e: java.lang.Exception) {
            return RequestResult.Error(errorMsg)
        }
    }

    override fun bagShowInfo(code: String): RequestResult<BagShowData> {
        TODO("Not yet implemented")
    }

    override fun addBag(code: String): RequestResult<Nothing> {
        TODO("Not yet implemented")
    }

    override fun bagWeight(): RequestResult<Nothing> {
        TODO("Not yet implemented")
    }

    override fun roomBagList(roomId: String): RequestResult<RoomBagListData> {
        TODO("Not yet implemented")
    }

    override fun editBagCategory(
        bagId: String, category: String, dialysis: String, placenta: String
    ): RequestResult<Nothing> {
        TODO("Not yet implemented")
    }

    override fun editBagWeight(bagId: String, weight: String): RequestResult<Nothing> {
        TODO("Not yet implemented")
    }

    override fun editBagQuality(
        force: String,
        bagId: String,
        unBroken: String,
        sterile: String,
        tight: String,
        classified: String,
        commodious: String,
        fewMedicalL: String
    ): RequestResult<Nothing> {
        TODO("Not yet implemented")
    }

    override fun printOneBag(bagId: String): RequestResult<Nothing> {
        TODO("Not yet implemented")
    }

    override fun printRoomBag(roomId: String): RequestResult<Nothing> {
        TODO("Not yet implemented")
    }

    override fun signRoomBag(roomId: String, signToken: String): RequestResult<Nothing> {
        TODO("Not yet implemented")
    }

    override fun batchNum(): RequestResult<String> {
        TODO("Not yet implemented")
    }

    override fun isBox(code: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun packBag(
        batchCode: String,
        boxCode: String,
        bagId: String
    ): RequestResult<BagPackData> {
        TODO("Not yet implemented")
    }

    override fun scanNurseInfo(signToken: String): RequestResult<NurseShowData> {
        TODO("Not yet implemented")
    }

    override fun institutionList(): RequestResult<InstitutionsData> {
        TODO("Not yet implemented")
    }

    override fun institutionMemberList(): RequestResult<InstitutionsMembersData> {
        TODO("Not yet implemented")
    }

    override fun stockBag(): RequestResult<BagShowData> {
        TODO("Not yet implemented")
    }

    override fun bagDeliver(boxCode: String, disMemberId: String): RequestResult<BagDeliverData> {
        TODO("Not yet implemented")
    }

    override fun signAgain(signToken: String, bagCode: String): RequestResult<Nothing> {
        TODO("Not yet implemented")
    }

    override fun deliverHis(page: String, limit: String): RequestResult<DeliverHisData> {
        TODO("Not yet implemented")
    }

    override fun getNursePackList(): RequestResult<NursePackListData> {
        TODO("Not yet implemented")
    }


}