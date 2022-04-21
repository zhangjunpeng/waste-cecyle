package com.nextmar.requestdata.datasource

import android.text.TextUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.nextmar.requestdata.RESTURL
import com.nextmar.requestdata.Result
import com.nextmar.requestdata.model.*
import com.nextmar.requestdata.repo.Repository
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


    override fun numberLogin(username: String, password: String): Result<NumberLoginData> {

        try {
            val formBody =
                FormBody.Builder().add("account", username).add("password", password).build()
            val request: Request = Request.Builder().url(RESTURL.NormalLogin).post(formBody).build()
            val call: Call = client.newCall(request)
            val response = call.execute()
            val dataStr = response.body!!.string()
            LogUtils.i(dataStr)
            if (response.isSuccessful) {
                val model = Json.decodeFromString<ResultModel<NumberLoginData>>(dataStr)
                if (model.res!!) {
//                    SPUtils.getInstance().put(NameSpace.IsLogin, true)
//                    SPUtils.getInstance().put(NameSpace.Token, loggedInUser.data.api_key)
                }
                return Result.Success(model.data as NumberLoginData)
            } else {
                val model = Json.decodeFromString<ResultModel<Nothing>>(dataStr)
                return Result.Error(
                    model
                )
            }
        } catch (e: java.lang.Exception) {
            return Result.Error(errorMsg)
        }
    }

    override fun memberShowInfo(id: String): Result<MemberShowData> {
        TODO("Not yet implemented")
    }

    override fun rooShowInfo(id: String): Result<RoomShowData> {
        TODO("Not yet implemented")
    }

    override fun carTotal(projectId: String, roomId: String): Result<CarTotalData> {
        TODO("Not yet implemented")
    }

    override fun bagShowInfo(code: String): Result<BagShowData> {
        TODO("Not yet implemented")
    }

    override fun addBag(code: String): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override fun bagWeight(): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override fun roomBagList(roomId: String): Result<RoomBagListData> {
        TODO("Not yet implemented")
    }

    override fun editBagCategory(
        bagId: String, category: String, dialysis: String, placenta: String
    ): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override fun editBagWeight(bagId: String, weight: String): Result<Nothing> {
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
    ): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override fun printOneBag(bagId: String): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override fun printRoomBag(roomId: String): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override fun signRoomBag(roomId: String, signToken: String): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override fun batchNum(): Result<String> {
        TODO("Not yet implemented")
    }

    override fun isBox(code: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override fun packBag(batchCode: String, boxCode: String, bagId: String): Result<BagPackData> {
        TODO("Not yet implemented")
    }

    override fun scanNurseInfo(signToken: String): Result<NurseShowData> {
        TODO("Not yet implemented")
    }

    override fun institutionList(): Result<InstitutionsData> {
        TODO("Not yet implemented")
    }

    override fun institutionMemberList(): Result<InstitutionsMembersData> {
        TODO("Not yet implemented")
    }

    override fun stockBag(): Result<BagShowData> {
        TODO("Not yet implemented")
    }

    override fun bagDeliver(boxCode: String, disMemberId: String): Result<BagDeliverData> {
        TODO("Not yet implemented")
    }

    override fun signAgain(signToken: String, bagCode: String): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override fun deliverHis(page: String, limit: String): Result<DeliverHisData> {
        TODO("Not yet implemented")
    }

    override fun getNursePackList(): Result<NursePackListData> {
        TODO("Not yet implemented")
    }


}