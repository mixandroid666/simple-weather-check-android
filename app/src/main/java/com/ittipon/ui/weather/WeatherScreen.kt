package com.ittipon.ui.weather

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState(
        initial = WeatherUiState.Idle
    )

    WeatherScreen(
        modifier = modifier,
        uiState = uiState,
        onSave = viewModel::getGeoCoding,
        onSelectCity = viewModel::getCurrentWeather
    )
}

@Composable
internal fun WeatherScreen(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState,
    onSelectCity: (lat: String, lon: String) -> Unit,
    onSave: (name: String) -> Unit,
) {
    Column(modifier) {
        var cityName by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                maxLines = 1,
                value = cityName,
                onValueChange = { newText ->
                    if (newText.length <= 30) {
                        cityName = newText
                    }
                }
            )

            Button(
                modifier = Modifier.width(96.dp),
                onClick = {
                    onSave(cityName)
                }
            ) {
                Text("Search")
            }
        }

        when (uiState) {
            is WeatherUiState.Idle -> {

            }

            is WeatherUiState.Loading -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }

            is WeatherUiState.Empty -> {
                Text("No city found")
            }

            is WeatherUiState.Error -> {
                Text(uiState.message)
            }

            is WeatherUiState.ShowCityList -> {
                LazyColumn {
                    items(uiState.cityList.size) { index ->
                        val item = uiState.cityList[index]
                        Column(
                            modifier = Modifier.clickable {
                                onSelectCity(
                                    item.lat.toString(),
                                    item.lon.toString()
                                )
                            },
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 4.dp),
                                text = "${item.name}\n ${item.state},  ${item.country}"
                            )

                            HorizontalDivider()
                        }
                    }
                }
            }

            is WeatherUiState.ShowCurrentWeather -> {
                Text("Temp : ${uiState.weather.main.temp}")
                Text("Humidity : ${uiState.weather.main.humidity}")
                Text("Wind speed : ${uiState.weather.wind.speed}")
                Text("Description : ${uiState.weather.weather[0].description}")
            }
        }
    }
}