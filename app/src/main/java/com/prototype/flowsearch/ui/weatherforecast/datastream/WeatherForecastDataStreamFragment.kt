/*
 * Copyright (C) 2020 Fatih Giris. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.prototype.flowsearch.ui.weatherforecast.datastream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import com.prototype.flowsearch.R
import com.prototype.flowsearch.shared.Result
import kotlinx.android.synthetic.main.fragment_weather_forecast_one_shot.*

@AndroidEntryPoint
class WeatherForecastDataStreamFragment : Fragment() {

    private val viewModel: WeatherForecastDataStreamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_weather_forecast_data_stream, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Observe weather forecast data stream
        viewModel.weatherForecast.observe(viewLifecycleOwner, Observer {
            when (it) {
                Result.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    // Update weather data
                    tvDegree.text = it.data.toString()
                }
                Result.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
