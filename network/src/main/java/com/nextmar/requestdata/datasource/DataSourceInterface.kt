package com.nextmar.requestdata.datasource

import com.nextmar.requestdata.model.*
import  com.nextmar.requestdata.RequestResult

interface DataSourceInterface {

    open fun numberLogin(username: String, password: String): RequestResult<LoginData>

    open fun scanLogin(code: String):RequestResult<LoginData>

    open fun memberShowInfo(token:String,id: String): RequestResult<MemberShowData>

    open fun rooShowInfo(token:String,id: String): RequestResult<RoomShowData>


    open fun carTotal(token:String,projectId: String, roomId: String?): RequestResult<CarTotalData>

    open fun bagShowInfo(token:String,code: String): RequestResult<BagShowData>

    open fun addBag(token:String,code: String): RequestResult<Any>

    open fun bagWeight(token:String,): RequestResult<Any>

    open fun roomBagList(token:String,roomId: String): RequestResult<RoomBagListData>

    open fun editBagCategory(token:String,
        bagId: String, category: String, dialysis: String, placenta: String
    ): RequestResult<Any>

    open fun editBagWeight(token:String,bagId: String, weight: String): RequestResult<Any>

    open fun editBagQuality(token:String,
        force: String,
        bagId: String,
        unBroken: String,
        sterile: String,
        tight: String,
        classified: String,
        commodious: String,
        fewMedicalL: String
    ): RequestResult<Any>

    open fun printOneBag(token:String,bagId: String): RequestResult<Any>

    open fun printRoomBag(token:String,roomId: String): RequestResult<Any>

    open fun signRoomBag(token:String,roomId: String, signToken: String): RequestResult<Any>

    open fun batchNum(token:String,): RequestResult<String>

    open fun isBox(token:String,code: String): RequestResult<Boolean>

    open fun packBag(token:String,batchCode: String, boxCode: String, bagId: String): RequestResult<BagPackData>

    open fun scanNurseInfo(token:String,signToken: String): RequestResult<NurseShowData>

    open fun institutionList(token:String,): RequestResult<InstitutionsData>

    open fun institutionMemberList(token:String,): RequestResult<InstitutionsMembersData>

    open fun stockBag(token:String,): RequestResult<BagShowData>

    open fun bagDeliver(token:String,boxCode: String, disMemberId: String): RequestResult<BagDeliverData>

    open fun signAgain(token:String,signToken: String, bagCode: String): RequestResult<Any>

    open fun deliverHis(token:String,page: String, limit: String): RequestResult<DeliverHisData>

    open fun getNursePackList(token:String,): RequestResult<NursePackListData>


}