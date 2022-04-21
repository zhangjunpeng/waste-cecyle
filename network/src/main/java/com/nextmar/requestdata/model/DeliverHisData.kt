// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json           = Json(JsonConfiguration.Stable)
// val deliverHisData = json.parse(DeliverHisData.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class DeliverHisDatum (
    @SerialName("dis_member_name")
    val disMemberName: String? = null,

    @SerialName("dis_name")
    val disName: String? = null,

    val boxTotal: String? = null,
    val date: String? = null
)
