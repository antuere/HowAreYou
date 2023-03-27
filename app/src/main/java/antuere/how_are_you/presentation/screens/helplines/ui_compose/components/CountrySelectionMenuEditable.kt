package antuere.how_are_you.presentation.screens.helplines.ui_compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
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
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.DefaultTextField
import antuere.how_are_you.util.extensions.getName
import antuere.how_are_you.util.extensions.upperCaseFirstCharacter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelectionMenuEditable(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    countries: List<SupportedCountry>,
    @DrawableRes flagId: Int,
    textFieldValue: String,
    onSelectedCountryChange: (SupportedCountry) -> Unit,
    onSelectedCountryFieldChange: (String, Map<String, SupportedCountry>) -> Unit,
    onDismiss: () -> Unit,
    onExpandedChange: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val sortedCountriesMap = remember(countries) {
        countries.associateBy {
            it.getName().asString(context).lowercase()
        }.toSortedMap()
    }
    val filteringCountries = remember(textFieldValue) {
        sortedCountriesMap.filterKeys {
            it.contains(textFieldValue, ignoreCase = true)
        }
    }

    LaunchedEffect(isExpanded) {
        if (!isExpanded) {
            focusManager.clearFocus()
        }
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { onExpandedChange() },
    ) {
        DefaultTextField(
            modifier = Modifier.menuAnchor(),
            value = textFieldValue,
            onValueChange = { newValue: String ->
                onSelectedCountryFieldChange(newValue, sortedCountriesMap)
            },
            label = stringResource(id = R.string.country),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                onDismiss()
            }),
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 5.dp),
                    painter = painterResource(id = flagId),
                    contentDescription = "Flag of country",
                    tint = Color.Unspecified
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
        )

        ExposedDropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
            expanded = isExpanded,
            onDismissRequest = {
                focusManager.clearFocus()
                onDismiss()
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
                            onSelectedCountryChange(country)
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
                        onDismiss()
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}