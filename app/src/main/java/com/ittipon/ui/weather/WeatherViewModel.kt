package com.ittipon.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittipon.data.WeatherRepository
import com.ittipon.model.CurrentWeatherResponse
import com.ittipon.model.GeoCodingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> get() = _uiState

    fun getGeoCoding(city: String) {
        viewModelScope.launch {
            weatherRepository.getGeoCoding(city)
                .onStart {
                    _uiState.value = WeatherUiState.Loading
                }
                .catch {
                    _uiState.value = WeatherUiState.Error(it.message.toString())
                }
                .collect {
                    if (it.isEmpty()) {
                        _uiState.value = WeatherUiState.Empty
                        return@collect
                    }
                    _uiState.value = WeatherUiState.ShowCityList(it)
                }
        }
    }

    fun getCurrentWeather(lat: String, lon: String) {
        viewModelScope.launch {
            weatherRepository.getCurrentWeather(lat = lat, lon = lon)
                .onStart {
                    _uiState.value = WeatherUiState.Loading
                }
                .catch {
                    _uiState.value = WeatherUiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = WeatherUiState.ShowCurrentWeather(it)
                }
        }
    }
}


sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data object Idle : WeatherUiState()
    data class ShowCityList(val cityList: List<GeoCodingResponse>) : WeatherUiState()
    data class ShowCurrentWeather(val weather: CurrentWeatherResponse) : WeatherUiState()
    data object Empty : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}
