// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json        = Json(JsonConfiguration.Stable)
// val bagPackData = json.parse(BagPackData.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class BagPackData (
    val code: String? = null,
    val category: String? = null,
    val weight: String? = null,

    @SerialName("room_name")
    val roomName: String? = null,

    @SerialName("nurse_name")
    val nurseName: String? = null
)
