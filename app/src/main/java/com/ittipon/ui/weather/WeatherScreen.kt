package com.ittipon.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ittipon.weather_check.R

@Composable
fun WeatherParentScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState(
        initial = WeatherUiState.Idle
    )

    WeatherScreen(
        modifier = modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        uiState = uiState,
        onSave = viewModel::getGeoCoding,
        onSelectCity = viewModel::getCurrentWeather,
        onTextChange = viewModel::emitIdle
    )
}

@Composable
internal fun WeatherScreen(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState,
    onSelectCity: (lat: String, lon: String) -> Unit,
    onSave: (name: String) -> Unit,
    onTextChange: () -> Unit = {}
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
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                maxLines = 1,
                value = cityName,
                placeholder = {
                    Text("Enter city name")
                },
                onValueChange = { newText ->
                    onTextChange()
                    if (newText.length <= 30) {
                        cityName = newText
                    }
                }
            )

            Button(
                modifier = Modifier
                    .width(96.dp)
                    .wrapContentHeight(),
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

            is WeatherUiState.NoCityFound -> {
                Text("No city found")
            }

            is WeatherUiState.Error -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(uiState.message)
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 4.dp),
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = "Error"
                    )
                }
            }

            is WeatherUiState.ShowCityList -> {
                LazyColumn {
                    items(uiState.cityList.size) { index ->
                        val item = uiState.cityList[index]
                        val lat = item.lat.toString()
                        val lon = item.lon.toString()

                        val name = item.name ?: "N/A"
                        val country = item.country ?: "N/A"
                        val state = item.state ?: "N/A"
                        Column(
                            modifier = Modifier.clickable {
                                onSelectCity(
                                    lat,
                                    lon
                                )
                            },
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 4.dp),
                                text = "$name\n $country,  $state"
                            )

                            HorizontalDivider()
                        }
                    }
                }
            }

            is WeatherUiState.ShowCurrentWeather -> {
                val temperature = uiState.weather.main?.temp ?: "N/A"
                val humidity = uiState.weather.main?.humidity ?: "N/A"
                val windSpeed = uiState.weather.wind?.speed ?: "N/A"
                val description =
                    uiState.weather.weather?.getOrNull(0)?.description ?: "No description available"

                Text("Temp : $temperature Celsius")
                Text("Humidity : $humidity %")
                Text("Wind speed : $windSpeed Meter/Sec")
                Text("Description : $description ")
            }
        }
    }
}