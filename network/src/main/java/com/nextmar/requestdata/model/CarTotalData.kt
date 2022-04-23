// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json         = Json(JsonConfiguration.Stable)
// val carTotalData = json.parse(CarTotalData.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class CarTotalData (
    val totalNum: Long? = null,
    val totalWeight: Double? = null,
    val list: List<ListElement>? = null
)

@Serializable
data class ListElement (
    val category: String? = null,
    val num: String? = null,
    val weight: Double? = null
)
