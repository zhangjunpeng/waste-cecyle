package com.nextmar.requestdata.datasource

import com.nextmar.requestdata.Result
import com.nextmar.requestdata.model.*

interface DataSourceInterface {

    open fun numberLogin(username: String, password: String): Result<NumberLoginData>

    open fun memberShowInfo(id: String): Result<MemberShowData>

    open fun rooShowInfo(id: String): Result<RoomShowData>


    open fun carTotal(projectId:String,roomId:String):com.nextmar.requestdata.Result<CarTotalData>

    open fun bagShowInfo(code: String): Result<BagShowData>

    open fun addBag(code: String): Result<Nothing>

}