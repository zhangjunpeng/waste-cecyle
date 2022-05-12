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

        val params = HashMap<String, String>()
        params["phone"] = username
        params["password"] = password

        return post<LoginData>(params, RESTURL.NormalLogin)
    }

    override fun scanLogin(code: String): RequestResult<LoginData> {

        val params = HashMap<String, String>()
        params["code"] = code
        return post<LoginData>(params, RESTURL.ScanLogin)


    }


    override fun memberShowInfo(token: String, id: String): RequestResult<MemberShowData> {
        val params = HashMap<String, String>()
        params["id"] = id
        return post<MemberShowData>(params, RESTURL.PersonInfo, token)

    }

    override fun scanRoomInfo(token: String, id: String): RequestResult<RoomShowData> {
        val params = HashMap<String, String>()
        params["code"] = id
        return get<RoomShowData>(params, RESTURL.CodeGetRoom, token)
    }


    override fun carTotal(
        token: String,
        projectId: String,
        roomId: String?
    ): RequestResult<CarTotalData> {

        val params = HashMap<String, String>()
        params["project_id"] = projectId
        if (roomId != null) {
            params["room_id"] = roomId

        }
        return get<CarTotalData>(params, RESTURL.CarTotal, token)
    }

    override fun bagShowInfo(token: String, code: String): RequestResult<BagShowData> {
        val params = HashMap<String, String>()
        params["code"] = code
        return post<BagShowData>(params, RESTURL.BagShow, token)
    }

    override fun addBag(token: String, code: String,params:HashMap<String,String>): RequestResult<Any> {
        params["code"] = code
        return post<Any>(params, RESTURL.WhiteBagAdd, token)
    }

    override fun bagWeight(token: String,weight: String): RequestResult<Any> {
        val params = HashMap<String, String>()
        params["weight"] = weight
        return get<Any>(params,RESTURL.BagWeight,token)
    }

    override fun roomBagList(token: String, roomId: String): RequestResult<RoomBagListData> {
        val params = HashMap<String, String>()
        params["room_id"] = roomId
       return get<RoomBagListData>(params,RESTURL.RoomBagList,token)
    }

    override fun editBagCategory(
        token: String,
        bagId: String,
        category: String,
        dialysis: String,
        placenta: String,
    ): RequestResult<Any> {
        val params = HashMap<String, String>()
        params["bag_id"] = bagId
        params["category"] = category
        params["dialysis"] = dialysis
        params["placenta"] = placenta
        return post<Any>(params, RESTURL.EditBagCate, token)
    }

    override fun editBagWeight(
        token: String,
        bagId: String,
        weight: String
    ): RequestResult<Any> {
        val params = HashMap<String, String>()
        params["bag_id"] = bagId
        params["weight"] = weight
        return post<Any>(params, RESTURL.EditBagWeight, token)
    }

    override fun editBagQuality(
        token: String,
        force: String,
        bagId: String,
        unBroken: String,
        sterile: String,
        tight: String,
        classified: String,
        commodious: String,
        fewMedicalL: String
    ): RequestResult<Any> {
        val params = HashMap<String, String>()
        params["force"] = "1"
        params["bag_id"] = bagId
        params["unBroken"] = unBroken
        params["sterile"] = sterile
        params["tight"] = tight
        params["classified"] = classified
        params["commodious"] = commodious
        params["few_medical"] = fewMedicalL

        return post<BagShowData>(params, RESTURL.EditBagQua, token)
    }

    override fun printOneBag(token: String, bagId: String): RequestResult<Any> {
        TODO("Not yet implemented")
    }

    override fun printRoomBag(token: String, roomId: String): RequestResult<Any> {
        val params = HashMap<String, String>()
        params["room_id"] = roomId
        return post<Any>(params, RESTURL.RoomBagPrint, token)
    }

    override fun signRoomBag(
        token: String,
        roomId: String,
        signToken: String
    ): RequestResult<Any> {
        val paramMap = HashMap<String, String>()
        paramMap["room_id"] = roomId
        paramMap["signToken"]
        return post<Any>(paramMap, RESTURL.RoomBagSign, token)
    }

    override fun batchNum(token: String): RequestResult<String> {
        TODO("Not yet implemented")
    }

    override fun isBox(token: String, code: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun packBag(
        token: String,
        batchCode: String,
        boxCode: String,
        bagId: String
    ): RequestResult<BagPackData> {
        TODO("Not yet implemented")
    }

    override fun scanNurseInfo(token: String, signToken: String): RequestResult<NurseShowData> {
        val paramMap = HashMap<String, String>()
        paramMap["signToken"] = signToken

        return get<NurseShowData>(paramMap, RESTURL.ScanNurseInfo, token)

    }

    override fun institutionList(token: String): RequestResult<InstitutionsData> {
        TODO("Not yet implemented")
    }

    override fun institutionMemberList(token: String): RequestResult<InstitutionsMembersData> {
        TODO("Not yet implemented")
    }

    override fun stockBag(token: String): RequestResult<BagShowData> {
        TODO("Not yet implemented")
    }

    override fun bagDeliver(
        token: String,
        boxCode: String,
        disMemberId: String
    ): RequestResult<BagDeliverData> {
        TODO("Not yet implemented")
    }

    override fun signAgain(
        token: String,
        signToken: String,
        bagCode: String
    ): RequestResult<Any> {
        TODO("Not yet implemented")
    }

    override fun deliverHis(
        token: String,
        page: String,
        limit: String
    ): RequestResult<DeliverHisData> {
        TODO("Not yet implemented")
    }

    override fun getNursePackList(token: String): RequestResult<NursePackListData> {
        TODO("Not yet implemented")
    }

    inline fun <reified T : Any> post(
        params: HashMap<String, String>,
        url: String,
        token: String? = null
    ): RequestResult<T> {
        try {
            val formBody =
                FormBody.Builder()
            params.keys.forEach { t ->
                formBody.add(t, params[t]!!)
            }
            val requestBuilder =
                Request.Builder()
            requestBuilder.url(url)
            if (token != null) {
                requestBuilder.addHeader("Token", token)
            }
            val request = requestBuilder.post(formBody.build())
                .build()
            val call: Call = client.newCall(request)
            val response = call.execute()
            val dataStr = response.body!!.string()
            LogUtils.i(dataStr)
            if (response.isSuccessful) {
                val model = Json.decodeFromString<ResultModel<T>>(dataStr)
                return if (model.res!!) {
                    RequestResult.Success(model.data as T)

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
            e.printStackTrace()
            return RequestResult.Error(
                errorMsg
            )
        }
    }

    inline fun <reified T : Any> get(
        params: HashMap<String, String>,
        url: String,
        token: String? = null
    ): RequestResult<T> {
        try {
            val urlStringBuffer = StringBuffer(url)
            if (params.keys.size > 0) {
                urlStringBuffer.append("?")
                params.keys.forEach { t ->
                    urlStringBuffer.append("$t=${params[t]}&")
                }
            }


            val requestBuilder =
                Request.Builder()
            requestBuilder.url(urlStringBuffer.toString())
            if (token != null) {
                requestBuilder.addHeader("Token", token)
            }
            val request = requestBuilder.get()
                .build()
            val call: Call = client.newCall(request)
            val response = call.execute()
            val dataStr = response.body!!.string()
            LogUtils.i(dataStr)
            if (response.isSuccessful) {
                val model = Json.decodeFromString<ResultModel<T>>(dataStr)
                return if (model.res!!) {
                    RequestResult.Success(model.data as T)

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
            e.printStackTrace()
            return RequestResult.Error(
                errorMsg
            )
        }
    }

}