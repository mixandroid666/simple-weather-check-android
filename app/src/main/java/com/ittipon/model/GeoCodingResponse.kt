package com.ittipon.model

import com.google.gson.annotations.SerializedName


data class GeoCodingResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("local_names")
    val localNames: Map<String, String>?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("state")
    val state: String?
)
