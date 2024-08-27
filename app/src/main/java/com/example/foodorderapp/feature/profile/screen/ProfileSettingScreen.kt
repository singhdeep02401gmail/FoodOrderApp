package com.example.foodorderapp.feature.profile.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodorderapp.feature.profile.AccSettingViewModel
import com.example.foodorderapp.feature.profile.profileSettings.payment.screens.paymentMethodListScreen.components.SettingTitle
import com.example.foodorderapp.feature.profile.Profile
import com.example.foodorderapp.feature.profile.data.AccountBasicSetting
import com.example.foodorderapp.feature.profile.data.MoreSetting
import com.example.foodorderapp.feature.profile.data.NotificationSetting
import com.example.foodorderapp.feature.profile.screen.components.EachItemDesign
import com.example.foodorderapp.feature.profile.screen.components.TopHeadingText
import com.example.foodorderapp.ui.theme.superLightGray

@Composable
fun ProfileSettingScreen(modifier: Modifier = Modifier, navController: NavController) {
    val settingViewModel: AccSettingViewModel = viewModel()
    val settingsList = settingViewModel.settingsList.collectAsState().value
    var iconSize by remember { mutableStateOf(0.dp) }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            TopHeadingText(Modifier.padding(16.dp))
        }
        settingsList.forEach { (title, list) ->
            item {
                SettingTitle(
                    modifier = Modifier.padding(16.dp),
                    title = title)
            }
            items(list) { item ->
                EachItemDesign(
                    title = title,
                    item = item,
                    iconWidthSize = { iconSize = it },
                    selectedItem = {
                        when (item) {
                            is AccountBasicSetting -> {
                                it as AccountBasicSetting
                                navController.navigate(it.route)
                            }
                            is NotificationSetting -> {}
                            is MoreSetting -> {
                                navController.navigate(Profile.ProfileInformation)
                            }
                        }
                    },
                    isSwitched = {
                            switch, item ->
                        item as NotificationSetting
                        settingViewModel.updatePushNotification(
                            value = switch,
                            id = item.id
                        )
                    }
                )

                if (item != list.last()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = iconSize),
                        color = superLightGray,
                        thickness = 1.dp
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


