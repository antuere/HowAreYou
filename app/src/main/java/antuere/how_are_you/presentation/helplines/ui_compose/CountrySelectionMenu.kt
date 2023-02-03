package antuere.how_are_you.presentation.helplines.ui_compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import antuere.domain.dto.helplines.SupportedCountry
import antuere.how_are_you.R
import antuere.how_are_you.util.getName
import antuere.how_are_you.util.upperCaseFirstCharacter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelectionMenu(
    modifier: Modifier = Modifier,
    countriesMap: Map<String, SupportedCountry>,
    selectedCountry: SupportedCountry,
    onSelectedCountryChange: (SupportedCountry) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    val selectedCountryName = remember(selectedCountry) {
        selectedCountry.getName().asString(context)
    }
    var selectedCountryText by remember(selectedCountry) { mutableStateOf(selectedCountryName) }

    var currentFlagId by remember { mutableStateOf(selectedCountry.flagId) }
    var isCountryCurrentText by remember { mutableStateOf(true) }

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
            onValueChange = { newValue ->
                val lowerCaseValue = newValue.lowercase()
                val foundCountry = countriesMap[lowerCaseValue]
                if (foundCountry != null) {
                    isCountryCurrentText = true
                    currentFlagId = foundCountry.flagId
                } else {
                    isCountryCurrentText = false
                }
                selectedCountryText = newValue
            },
            label = { Text(stringResource(id = R.string.country)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                selectedCountryText = selectedCountryName
                currentFlagId = selectedCountry.flagId
                isCountryCurrentText = true
                expanded = false
            }),
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 5.dp),
                    painter = if (isCountryCurrentText) painterResource(id = currentFlagId) else painterResource(
                        id = antuere.data.R.drawable.flag_plug
                    ),
                    contentDescription = "Flag of country",
                    tint = Color.Unspecified
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )

        val filteringCountries =
            countriesMap.filterKeys {
                it.contains(selectedCountryText, ignoreCase = true)
            }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                focusManager.clearFocus()
                selectedCountryText = selectedCountryName
                currentFlagId = selectedCountry.flagId
                isCountryCurrentText = true
                expanded = false
            },
        ) {
            if (filteringCountries.isNotEmpty()) {
                filteringCountries.forEach { (name, country) ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                if (country is SupportedCountry.USA) {
                                    name.uppercase()
                                } else {
                                    name.upperCaseFirstCharacter()
                                }
                            )
                        },
                        onClick = {
                            focusManager.clearFocus()
                            selectedCountryText = name.upperCaseFirstCharacter()
                            currentFlagId = country.flagId
                            isCountryCurrentText = true
                            onSelectedCountryChange(country)
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = country.flagId),
                                contentDescription = "Flag of country",
                                tint = Color.Unspecified
                            )
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
                        currentFlagId = selectedCountry.flagId
                        isCountryCurrentText = true
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}