// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json           = Json(JsonConfiguration.Stable)
// val bagDeliverData = json.parse(BagDeliverData.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class BagDeliverData (
    val instockTotal: String? = null,
    val deliverTotal: String? = null,
    val boxCode: String? = null,
    val bagTotal: Long? = null,
    val boxWeight: Double? = null,

    @SerialName("instock_time")
    val instockTime: String? = null
)
