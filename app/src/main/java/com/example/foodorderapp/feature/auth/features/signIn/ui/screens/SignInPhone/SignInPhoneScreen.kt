package com.example.foodorderapp.feature.auth.features.signIn.ui.screens.SignInPhone

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.foodorderapp.R
import com.example.foodorderapp.components.AppHeader
import com.example.foodorderapp.components.RegisterLayout
import com.example.foodorderapp.feature.auth.features.signIn.ui.screens.SignInPhone.components.DropDownCountryList
import com.example.foodorderapp.feature.auth.features.signIn.ui.screens.SignInPhone.components.GetStartedWithFoodText
import com.example.foodorderapp.feature.auth.features.signIn.ui.screens.SignInPhone.components.TextBelowGetStarted
import com.example.foodorderapp.feature.auth.features.signIn.ui.screens.SignInPhone.components.listOfCountries
import com.example.foodorderapp.feature.auth.features.signIn.ui.screens.components.EmailAddressTextWithInput

import com.example.foodorderapp.feature.auth.features.signUp.ui.screen.components.SignUpButton

@Composable
fun SignInPhoneScreen(modifier: Modifier = Modifier) {

    var selectedCountry by remember { mutableStateOf(listOfCountries.first()) }
    var numberWithCountryCode by remember { mutableStateOf(selectedCountry.code) }
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    RegisterLayout(
        modifier = modifier
            .pointerInput(key1 = Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        topBar = {
            AppHeader(title = "Login to Tamang food\nservice",
                onBack = {}
            )
        },
        content = {
            item {
                GetStartedWithFoodText()
                TextBelowGetStarted()
                EmailAddressTextWithInput(
                    modifier = Modifier
                        .padding(top = 64.dp, bottom = 96.dp),
                    label = stringResource(R.string.phone_number),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    leadingIcon = {
                        DropDownCountryList(
                            selectedCountry = selectedCountry,
                            expanded = expanded,
                            onClicked = { expanded = !expanded },
                            onDismissRequest = { expanded = false },
                            itemClicked = {
                                selectedCountry = it
                                numberWithCountryCode = it.code
                                expanded = false
                            }
                        )
                    },
                    showIcon = false,
                    value = numberWithCountryCode,
                    onValueChange = {
                        numberWithCountryCode = it
                    }
                )
                SignUpButton {
                }
            }
        }
    )
}