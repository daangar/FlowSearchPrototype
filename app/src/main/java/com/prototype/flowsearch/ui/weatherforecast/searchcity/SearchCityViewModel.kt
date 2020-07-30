
package com.prototype.flowsearch.ui.weatherforecast.searchcity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class SearchCityViewModel @ViewModelInject constructor() : ViewModel() {
    val cityList = listOf(
        "Los Angeles", "Chicago", "Indianapolis", "Phoenix", "Houston",
        "Denver", "Las Vegas", "Philadelphia", "Portland", "Seattle"
    )

    val cityFilterChannel = ConflatedBroadcastChannel<String>()

    @FlowPreview
    val cityFilterFlow: Flow<List<String>> = cityFilterChannel
        .asFlow()
        .map {
            val filteredCities = filterCities(it)

            delay(500)

            filteredCities
        }

    override fun onCleared() {
        super.onCleared()

        cityFilterChannel.close()
    }

    private fun filterCities(key: String): List<String> {
        return cityList.filter {
            it.contains(key)
        }
    }
}