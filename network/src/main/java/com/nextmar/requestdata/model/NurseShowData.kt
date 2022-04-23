// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json          = Json(JsonConfiguration.Stable)
// val nurseShowData = json.parse(NurseShowData.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class NurseShowData (
    val id: String? = null,

    @SerialName("open_id")
    val openID: JsonObject? = null,

    @SerialName("project_id")
    val projectID: String? = null,

    @SerialName("room_id")
    val roomID: String? = null,

    val signToken: String? = null,
    val name: String? = null,
    val nickname: JsonObject? = null,
    val sex: JsonObject? = null,
    val title: JsonObject? = null,
    val avatar: JsonObject? = null,

    @SerialName("is_auth")
    val isAuth: String? = null,

    val phone: String? = null,
    val identity: JsonObject? = null,
    val health: JsonObject? = null,
    val roomName: String? = null,
    val address: String? = null,
    val note: String? = null,
    val status: String? = null,

    @SerialName("add_time")
    val addTime: String? = null,

    @SerialName("last_time")
    val lastTime: String? = null,

    val token: JsonObject? = null
)
