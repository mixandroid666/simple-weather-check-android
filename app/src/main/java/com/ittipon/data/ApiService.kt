package com.ittipon.data

import com.ittipon.model.CurrentWeatherResponse
import com.ittipon.model.GeoCodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") appId: String,
    ): CurrentWeatherResponse

    @GET("/geo/1.0/direct")
    suspend fun getGeoCoding(
        @Query("q") city: String,
        @Query("limit") limit: String,
        @Query("appid") appId: String,
    ): List<GeoCodingResponse>

}