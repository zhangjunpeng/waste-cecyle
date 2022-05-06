package com.nextmar.requestdata.datasource

import com.nextmar.requestdata.model.*
import  com.nextmar.requestdata.RequestResult

interface DataSourceInterface {

    open fun numberLogin(username: String, password: String): RequestResult<Any>

    open fun memberShowInfo(token:String,id: String): RequestResult<MemberShowData>

    open fun rooShowInfo(id: String): RequestResult<RoomShowData>


    open fun carTotal(token:String,projectId: String, roomId: String): RequestResult<CarTotalData>

    open fun bagShowInfo(code: String): RequestResult<BagShowData>

    open fun addBag(code: String): RequestResult<Nothing>

    open fun bagWeight(): RequestResult<Nothing>

    open fun roomBagList(roomId: String): RequestResult<RoomBagListData>

    open fun editBagCategory(
        bagId: String, category: String, dialysis: String, placenta: String
    ): RequestResult<Nothing>

    open fun editBagWeight(bagId: String, weight: String): RequestResult<Nothing>

    open fun editBagQuality(
        force: String,
        bagId: String,
        unBroken: String,
        sterile: String,
        tight: String,
        classified: String,
        commodious: String,
        fewMedicalL: String
    ): RequestResult<Nothing>

    open fun printOneBag(bagId: String): RequestResult<Nothing>

    open fun printRoomBag(roomId: String): RequestResult<Nothing>

    open fun signRoomBag(roomId: String, signToken: String): RequestResult<Nothing>

    open fun batchNum(): RequestResult<String>

    open fun isBox(code: String): RequestResult<Boolean>

    open fun packBag(batchCode: String, boxCode: String, bagId: String): RequestResult<BagPackData>

    open fun scanNurseInfo(signToken: String): RequestResult<NurseShowData>

    open fun institutionList(): RequestResult<InstitutionsData>

    open fun institutionMemberList(): RequestResult<InstitutionsMembersData>

    open fun stockBag(): RequestResult<BagShowData>

    open fun bagDeliver(boxCode: String, disMemberId: String): RequestResult<BagDeliverData>

    open fun signAgain(signToken: String, bagCode: String): RequestResult<Nothing>

    open fun deliverHis(page: String, limit: String): RequestResult<DeliverHisData>

    open fun getNursePackList(): RequestResult<NursePackListData>


}