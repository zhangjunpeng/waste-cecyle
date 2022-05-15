// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json        = Json(JsonConfiguration.Stable)
// val bagShowData = json.parse(BagShowData.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class BagShowData (
    val id: String? = null,
    val code: String? = null,

    @SerialName("project_id")
    val projectID: String? = null,

    @SerialName("room_id")
    val roomID: String? = null,

    @SerialName("nurse_id")
    val nurseID: String? = null,

    @SerialName("member_id")
    val memberID: String? = null,

    @SerialName("store_member_id")
    val storeMemberID: JsonObject? = null,

    @SerialName("dis_member_id")
    val disMemberID: JsonObject? = null,

    @SerialName("mdays_id")
    val mdaysID: String? = null,

    @SerialName("pdays_id")
    val pdaysID: String? = null,

    val weight: String? = null,
    val category: String? = null,
    val info: String? = null,
    val batch: String? = null,

    @SerialName("box_code")
    val boxCode: String? = null,

    val status: String? = null,

    @SerialName("reg_time")
    val regTime: JsonObject? = null,

    @SerialName("scan_time")
    val scanTime: JsonObject? = null,

    @SerialName("collect_time")
    val collectTime: JsonObject? = null,

    @SerialName("instock_time")
    val instockTime: String? = null,

    @SerialName("deliver_time")
    val deliverTime: JsonObject? = null,

    @SerialName("add_time")
    val addTime: String? = null,

    @SerialName("sign_img")
    val signImg: String? = null,

    @SerialName("sign_time")
    val signTime: String? = null,

    @SerialName("last_time")
    val lastTime: String? = null,

    val rname: String? = null,
    val mname: String? = null,
    val nname: String? = null,
    val nrname: String? = null,
    val quality: Quality? = null
)

@Serializable
data class Quality (
    val id: String? = null,

    @SerialName("bag_id")
    val bagID: String? = null,

    val unbroken: String? = null,
    val sterile: String? = null,
    val tight: String? = null,
    val classified: String? = null,
    val commodious: String? = null,
    val dialysis: String? = null,
    val placenta: String? = null,

    @SerialName("few_medical")
    val fewMedical: String? = null,

    val role: String? = null,

    @SerialName("action_id")
    val actionID: String? = null,

    @SerialName("add_time")
    val addTime: String? = null,

    @SerialName("last_time")
    val lastTime: String? = null
)
