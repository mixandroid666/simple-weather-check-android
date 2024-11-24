package com.ittipon.data.network.di

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("code")
    var code: Int = -1,
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("message")
    var errorMessage: Any? = null
)
