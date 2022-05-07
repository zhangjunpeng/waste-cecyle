package com.nextmar.requestdata.model

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json            = Json(JsonConfiguration.Stable)
// val numberLoginData = json.parse(NumberLoginData.serializer(), jsonString)


import kotlinx.serialization.*

@Serializable
data class LoginData (
    val id: String? = null,

    @SerialName("project_id")
    val projectID: String? = null,

    val role: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val password: String? = null,
    val status: String? = null,

    @SerialName("add_time")
    val addTime: String? = null,

    val categorys: String? = null,
    val type: String? = null,
    val token: String? = null,

    @SerialName("last_time")
    val lastTime: String? = null
)
