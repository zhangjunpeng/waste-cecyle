package com.nextmar.requestdata.model

import kotlinx.serialization.Serializable

@Serializable
data class AddBagData(
    val signNum: Long? = null,
    val notSignNum: Long? = null,
    val weight: Double? = null
)

