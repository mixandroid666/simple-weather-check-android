package com.ittipon.data

import com.ittipon.model.CurrentWeatherResponse
import com.ittipon.model.GeoCodingResponse
import com.ittipon.weather_check.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface WeatherRepository {

    fun getGeoCoding(city: String): Flow<List<GeoCodingResponse>>
    fun getCurrentWeather(lat: String, lon: String): Flow<CurrentWeatherResponse>
}

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : WeatherRepository {

    override fun getGeoCoding(city: String): Flow<List<GeoCodingResponse>> =
        flow {
            val response = apiService.getGeoCoding(
                city = city,
                limit = "10",
                appId = BuildConfig.WEATHER_APP_API_KEY
            )
            emit(response)
        }

    override fun getCurrentWeather(
        lat: String,
        lon: String
    ): Flow<CurrentWeatherResponse> = flow {
        val response = apiService.getCurrentWeather(
            lat = lat,
            lon = lon,
            units = "metric",
            appId = BuildConfig.WEATHER_APP_API_KEY
        )
        emit(response)
    }
}