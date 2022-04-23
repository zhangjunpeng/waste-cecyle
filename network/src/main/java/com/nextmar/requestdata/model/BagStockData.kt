package com.nextmar.requestdata.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class BagStockData(
    @SerialName("deliverTotal")
    val deliverTotal: String? = "",
    @SerialName("instockTotal")
    val instockTotal: String? = ""
)