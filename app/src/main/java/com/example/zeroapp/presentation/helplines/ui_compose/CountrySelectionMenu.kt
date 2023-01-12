package com.example.zeroapp.presentation.helplines.ui_compose

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import antuere.domain.dto.helplines.SupportedCountry
import com.example.zeroapp.R
import com.example.zeroapp.util.getName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelectionMenu(
    modifier: Modifier = Modifier,
    countries: List<SupportedCountry>,
    selectedCountry: SupportedCountry,
    onSelectedCountryChange: (SupportedCountry) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val selectedCountryName = selectedCountry.getName()
    var expanded by remember { mutableStateOf(false) }

    var selectedCountryText by remember { mutableStateOf(selectedCountryName) }

    LaunchedEffect(expanded) {
        if (!expanded) {
            focusManager.clearFocus()
        }
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedCountryText,
            onValueChange = { selectedCountryText = it },
            label = { Text(stringResource(id = R.string.country)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                selectedCountryText = selectedCountryName
                expanded = false
            }),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        val filteringCountries =
            countries.filter {
                it.getName().contains(
                    selectedCountryText,
                    ignoreCase = true
                )
            }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                selectedCountryText = selectedCountryName
                focusManager.clearFocus()
            },
        ) {
            if (filteringCountries.isNotEmpty()) {
                filteringCountries.forEach { selectionCountry ->
                    val countryName = selectionCountry.getName()

                    DropdownMenuItem(
                        text = { Text(countryName) },
                        onClick = {
                            focusManager.clearFocus()
                            selectedCountryText = countryName
                            onSelectedCountryChange(selectionCountry)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            } else {
                val notSupportedCountry = stringResource(id = R.string.notSupportedCountry)
                DropdownMenuItem(
                    text = { Text(notSupportedCountry) },
                    onClick = {
                        focusManager.clearFocus()
                        selectedCountryText = selectedCountryName
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}