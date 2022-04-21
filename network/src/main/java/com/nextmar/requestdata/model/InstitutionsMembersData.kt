// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json                    = Json(JsonConfiguration.Stable)
// val institutionsMembersData = json.parse(InstitutionsMembersData.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class InstitutionsMembersDatum (
    val id: String? = null,

    @SerialName("dis_id")
    val disID: String? = null,

    val name: String? = null,
    val phone: String? = null,
    val state: String? = null,

    @SerialName("add_time")
    val addTime: String? = null,

    @SerialName("last_time")
    val lastTime: String? = null
)
