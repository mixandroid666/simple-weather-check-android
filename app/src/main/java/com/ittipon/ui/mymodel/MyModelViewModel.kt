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

package com.ittipon.ui.mymodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittipon.data.MyModelRepository
import com.ittipon.data.WeatherRepository
import com.ittipon.data.network.di.GeoCodingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyModelViewModel @Inject constructor(
    private val myModelRepository: MyModelRepository,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _geoCodingData = MutableStateFlow<UiState<List<GeoCodingResponse>>>(
        UiState.ShowCityList(
            listOf()
        )
    )
    val geoCodingData: StateFlow<UiState<List<GeoCodingResponse>>> get() = _geoCodingData


//    val uiState: StateFlow<MyModelUiState> = myModelRepository
//        .myModels.map<List<String>, MyModelUiState>(::Success)
//        .catch { emit(Error(it)) }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addMyModel(name: String) {
        viewModelScope.launch {
            myModelRepository.add(name)
        }
    }


    fun getGeoCoding(city: String) {
        viewModelScope.launch {
            weatherRepository.getGeoCodingData(city).collect { result ->
                _geoCodingData.value = result
            }
        }
    }
}

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class ShowCityList<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
