package com.ittipon.data.network.di

data class GeoCodingResponse(
    val a: String? = null, // Or whatever type 'a' should be
    val issuccess: Boolean,
    val code: Int,
    val message: String,
    val data: List<GeoCodingData>
)

data class GeoCodingData(
    val name: String,
    val local_names: Map<String, String>,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)