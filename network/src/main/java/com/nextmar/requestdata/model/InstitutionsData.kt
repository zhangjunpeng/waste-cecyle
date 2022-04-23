// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json             = Json(JsonConfiguration.Stable)
// val institutionsData = json.parse(InstitutionsData.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*

@Serializable
data class InstitutionsData (
    val id: String? = null,
    val name: String? = null,
    val region: String? = null,
    val info: String? = null,
    val state: String? = null,

    @SerialName("add_time")
    val addTime: String? = null,

    @SerialName("last_time")
    val lastTime: String? = null
)
