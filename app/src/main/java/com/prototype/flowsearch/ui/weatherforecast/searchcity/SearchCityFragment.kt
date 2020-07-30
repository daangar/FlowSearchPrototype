
package com.prototype.flowsearch.ui.weatherforecast.searchcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import com.prototype.flowsearch.R
import kotlinx.android.synthetic.main.fragment_search_city.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchCityFragment : Fragment() {

    private val viewModel: SearchCityViewModel by viewModels()
    private lateinit var adapter: SearchCityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search_city, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        observeFilteredCities()
    }

    private fun initView() {
        etCity.doAfterTextChanged {
            val key = it.toString()

            pbLoading.show()

            viewModel.cityFilterChannel.offer(key)
        }

        adapter = SearchCityAdapter(viewModel.cityList)
        rvCity.adapter = adapter
    }


    private fun observeFilteredCities() {
        lifecycleScope.launchWhenStarted {
            viewModel.cityFilterFlow.collect { filteredCities ->

                pbLoading.hide()

                adapter.setItems(filteredCities)
            }
        }
    }
}