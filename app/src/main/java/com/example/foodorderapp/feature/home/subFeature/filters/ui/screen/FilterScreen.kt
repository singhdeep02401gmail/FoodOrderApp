package com.example.foodorderapp.feature.home.subFeature.filters.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foodorderapp.R
import com.example.foodorderapp.components.CustomButton
import com.example.foodorderapp.feature.home.subFeature.filters.domain.mainList
import com.example.foodorderapp.feature.home.subFeature.filters.ui.screen.components.ApplyFilterButtonText
import com.example.foodorderapp.feature.home.subFeature.filters.ui.screen.components.FilterCommonLayout
import com.example.foodorderapp.feature.home.subFeature.filters.ui.screen.components.FilterParameter
import com.example.foodorderapp.feature.home.subFeature.filters.ui.screen.components.PriceText
import com.example.foodorderapp.feature.home.subFeature.filters.ui.screen.components.RoundedFilterChip
import com.example.foodorderapp.feature.home.subFeature.filters.ui.screen.components.isSelectedItem
import com.example.foodorderapp.feature.home.subFeature.filters.ui.screen.components.updated
import com.example.foodorderapp.feature.home.subFeature.filters.ui.viewModel.FilterViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    val filterViewModel = viewModel<FilterViewModel>()
    val uiState = filterViewModel.selectedParametersState.collectAsState().value

    FilterCommonLayout(
        modifier = modifier,
        content = {
            mainList.forEach { (parameter, list) ->
                item {
                    FilterParameter(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = parameter,
                        onClick = { filterViewModel.clear(parameter) }
                    )
                }
                item {
                    if (parameter == stringResource(R.string.price_range)) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 64.dp),
                        ) {
                            item { Spacer(modifier = Modifier.padding(horizontal = 8.dp)) }
                            items(list, key = { it }) {
                                RoundedFilterChip(selected = it == uiState.priceRange,
                                    onClick = {
                                        filterViewModel.updateSelectedParameters(
                                            uiState.copy(priceRange = it)
                                        )
                                    }) { PriceText(text = it, isSelected = it == uiState.priceRange) }
                            }
                        }
                    } else {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 36.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            list.forEach { item ->
                                FilterChip(
                                    selected = isSelectedItem(parameter, item, uiState),
                                    onClick = {
                                        filterViewModel.updateSelectedParameters(
                                            updated(parameter, item, uiState)
                                        )
                                    },
                                    label = { Text(text = item) },
                                    border = null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                        labelColor = MaterialTheme.colorScheme.onBackground,
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomButton = {
            CustomButton(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                onClick = { navController.popBackStack() }, // passing filtered data is pending
                content = { ApplyFilterButtonText() }
            )
        }
    )
}