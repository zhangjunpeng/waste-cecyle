package com.nextmar.requestdata.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class NursePackListData(
    @SerialName("count")
    val count: Int? = 0,
    @SerialName("list")
    val list: List<NursePackListDatum>? = listOf()
)

@Serializable
data class NursePackListDatum(
    @SerialName("bagNum")
    val bagNum: String? = "",
    @SerialName("notenote")
    val notenote: String? = "",
    @SerialName("room_name")
    val roomName: String? = "",
    @SerialName("time")
    val time: String? = ""
)