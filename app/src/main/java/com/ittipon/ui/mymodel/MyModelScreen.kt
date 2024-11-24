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

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ittipon.data.network.di.GeoCodingResponse

@Composable
fun MyModelScreen(
    modifier: Modifier = Modifier,
    viewModel: MyModelViewModel = hiltViewModel()
) {
    val result by viewModel.geoCodingData.collectAsState(initial = UiState.ShowCityList(listOf()))

    IdleScreen(
        modifier = modifier,
        uiState = result,
        onSave = viewModel::getGeoCoding,
    )
}


@Composable
internal fun IdleScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<GeoCodingResponse>> = UiState.ShowCityList(listOf()),
    onSave: (name: String) -> Unit,
) {
    Column(modifier) {
        var nameMyModel by remember { mutableStateOf("london") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = nameMyModel,
                onValueChange = { nameMyModel = it }
            )

            Button(modifier = Modifier.width(96.dp), onClick = { onSave(nameMyModel) }) {
                Text("Search")
            }

        }

        when (uiState) {
            is UiState.Loading -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }

            }

            is UiState.Error -> {

            }

            is UiState.ShowCityList -> {
                LazyColumn {
                    items(uiState.data.size) { index ->
                        val item = uiState.data[index]

                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = "${item.name}\n ${item.state},  ${item.country}"
                        )

                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

// Previews
//
//@Preview(showBackground = true)
//@Composable
//private fun DefaultPreview() {
//    MyApplicationTheme {
//        MyModelScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
//    }
//}
//
//@Preview(showBackground = true, widthDp = 480)
//@Composable
//private fun PortraitPreview480() {
//    MyApplicationTheme {
//        MyModelScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
//    }
//}
//
//@Preview(showBackground = true, widthDp = 510)
//@Composable
//private fun PortraitPreview510() {
//    MyApplicationTheme {
//        MyModelScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
//    }
//}
