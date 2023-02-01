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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelectionMenu(
    modifier: Modifier = Modifier,
    countries: List<SupportedCountry>,
    selectedCountry: SupportedCountry,
    onSelectedCountryChange: (SupportedCountry) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val selectedCountryName = selectedCountry.getName().asString()
    var expanded by remember { mutableStateOf(false) }
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
            onValueChange = {
                val foundCountry = countries.find { country ->
                    (country.getName().asString(context).equals(it, ignoreCase = true))
                }
                if (foundCountry != null) {
                    isCountryCurrentText = true
                    currentFlagId = foundCountry.flagId
                } else {
                    isCountryCurrentText = false
                }
                selectedCountryText = it
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
            countries.filter {
                it.getName().asString().contains(
                    selectedCountryText,
                    ignoreCase = true
                )
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
                filteringCountries.forEach { selectionCountry ->
                    val countryName = selectionCountry.getName().asString()

                    DropdownMenuItem(
                        text = { Text(countryName) },
                        onClick = {
                            focusManager.clearFocus()
                            selectedCountryText = countryName
                            currentFlagId = selectionCountry.flagId
                            isCountryCurrentText = true
                            onSelectedCountryChange(selectionCountry)
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = selectionCountry.flagId),
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