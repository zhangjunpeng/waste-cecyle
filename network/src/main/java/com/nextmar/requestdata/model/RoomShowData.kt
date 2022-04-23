package com.nextmar.requestdata.model

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json         = Json(JsonConfiguration.Stable)
// val roomShowData = json.parse(RoomShowData.serializer(), jsonString)


import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class RoomShowData (
    val id: String? = null,

    @SerialName("project_id")
    val projectID: String? = null,

    val name: String? = null,
    val building: String? = null,
    val info: String? = null,
    val iswrite: String? = null,
    val status: String? = null,

    @SerialName("add_time")
    val addTime: String? = null,

    @SerialName("last_time")
    val lastTime: String? = null,

    val count: String? = null,
    val notenote: String? = null,
    val password: String? = null
)
