package com.ittipon.data.network.di

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("users/{user}/repos")
    suspend fun getCurrentWeatherData(@Path("user") user: String?): String

    @GET("/geo/1.0/direct")
    suspend fun getGeoCodingData(
        @Query("q") city : String,
        @Query("limit") limit : String,
        @Query("appid") appId : String,
    ): List<GeoCodingResponse>

}