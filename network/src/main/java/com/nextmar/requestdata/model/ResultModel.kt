// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json        = Json(JsonConfiguration.Stable)
// val resultModel = json.parse(ResultModel.serializer(), jsonString)

package com.nextmar.requestdata.model

import kotlinx.serialization.*

@Serializable
data class ResultModel<T> (
    val res: Boolean? = null,
    val code: String? = null,
    val msg: String? = null,
    val data: T?= null,
    val count: Long? = null
)

