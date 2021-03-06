// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json            = Json(JsonConfiguration.Stable)
// val roomBagListData = json.parse(RoomBagListData.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class RoomBagListData (
    val signNum: Long? = null,
    val notSignNum: Long? = null,
    val weight: Double? = null,
    val list: List<RoomBagListDataElement>? = null
)

@Serializable
data class RoomBagListDataElement (
    val status: String? = null,

    @SerialName("is_print")
    val isPrint: String? = null,

    val category: String? = null,

    @SerialName("nurse_id")
    val nurseID: String? = null,

    val weight: Double? = null,

    @SerialName("scan_time")
    val scanTime: String? = null,

    @SerialName("nurse_name")
    val nurseName: String? = null
)
