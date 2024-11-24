/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ittipon.data

import com.ittipon.data.network.di.ApiService
import com.ittipon.data.network.di.GeoCodingResponse
import com.ittipon.ui.mymodel.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

interface WeatherRepository {

    fun getGeoCodingData(city: String): Flow<UiState<List<GeoCodingResponse>>>
}

class DefaultWeatherRepository @Inject constructor(
    private val apiService: ApiService,
) : WeatherRepository {

    override fun getGeoCodingData(city: String): Flow<UiState<List<GeoCodingResponse>>> =
        flow {
            try {
                emit(UiState.Loading)
                val response = apiService.getGeoCodingData(
                    city = city,
                    limit = "10",
                    appId = "2226376508ff06be82314db32f213f02"
                )
                emit(UiState.ShowCityList(response))
            } catch (e: HttpException) {
                emit(UiState.Error("HTTP error: ${e.message}"))
            } catch (e: IOException) {
                emit(UiState.Error("Network error: ${e.message}"))
            } catch (e: Exception) {
                emit(UiState.Error("Unexpected error: ${e.message}"))
            }


        }
}