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
import okhttp3.ResponseBody
import javax.inject.Inject

interface WeatherRepository {

    suspend fun getGeoCodingData(city: String): ResponseBody
}

class DefaultWeatherRepository @Inject constructor(
    private val apiService: ApiService,
) : WeatherRepository {

    override suspend fun getGeoCodingData(city: String): ResponseBody {
//        return "test"
        return apiService.getGeoCodingData(
            city = city,
            limit = "1",
            appId = "2226376508ff06be82314db32f213f02"
        )
    }
}