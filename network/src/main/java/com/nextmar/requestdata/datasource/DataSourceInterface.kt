package com.nextmar.requestdata.datasource

import com.nextmar.requestdata.model.*
import  com.nextmar.requestdata.Result

interface DataSourceInterface {

    open fun numberLogin(username: String, password: String): Result<NumberLoginData>

    open fun memberShowInfo(id: String): Result<MemberShowData>

    open fun rooShowInfo(id: String): Result<RoomShowData>


    open fun carTotal(projectId: String, roomId: String): Result<CarTotalData>

    open fun bagShowInfo(code: String): Result<BagShowData>

    open fun addBag(code: String): Result<Nothing>

    open fun bagWeight(): Result<Nothing>

    open fun roomBagList(roomId: String): Result<RoomBagListData>

    open fun editBagCategory(
        bagId: String, category: String, dialysis: String, placenta: String
    ): Result<Nothing>

    open fun editBagWeight(bagId: String, weight: String): Result<Nothing>

    open fun editBagQuality(
        force: String,
        bagId: String,
        unBroken: String,
        sterile: String,
        tight: String,
        classified: String,
        commodious: String,
        fewMedicalL: String
    ): Result<Nothing>

    open fun printOneBag(bagId: String): Result<Nothing>

    open fun printRoomBag(roomId: String): Result<Nothing>

    open fun signRoomBag(roomId: String, signToken: String): Result<Nothing>

    open fun batchNum(): Result<String>

    open fun isBox(code: String): Result<Boolean>

    open fun packBag(batchCode: String, boxCode: String, bagId: String): Result<BagPackData>

    open fun scanNurseInfo(signToken: String): Result<NurseShowData>

    open fun institutionList(): Result<InstitutionsData>

    open fun institutionMemberList(): Result<InstitutionsMembersData>

    open fun stockBag(): Result<BagShowData>

    open fun bagDeliver(boxCode: String, disMemberId: String): Result<BagDeliverData>

    open fun signAgain(signToken: String, bagCode: String): Result<Nothing>

    open fun deliverHis(page: String, limit: String): Result<DeliverHisData>

    open fun getNursePackList(): Result<NursePackListData>


}