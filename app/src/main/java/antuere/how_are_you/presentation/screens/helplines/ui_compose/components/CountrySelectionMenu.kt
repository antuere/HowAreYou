package antuere.how_are_you.presentation.screens.helplines.ui_compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import antuere.domain.dto.helplines.SupportedCountry
import antuere.how_are_you.R
import antuere.how_are_you.util.extensions.getName
import antuere.how_are_you.util.extensions.upperCaseFirstCharacter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelectionMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    countries: List<SupportedCountry>,
    @DrawableRes flagId: Int,
    textFieldValue: String,
    onSelectedCountryChange: (SupportedCountry) -> Unit,
    onExpandedChange: () -> Unit,
) {
    val context = LocalContext.current
    val countriesMap = remember(countries) {
        countries.associateBy {
            it.getName().asString(context).lowercase()
        }.toSortedMap()
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { onExpandedChange() },
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = textFieldValue,
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.country)) },
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
            onDismissRequest = { onExpandedChange() },
        ) {
            countriesMap.forEach { (name, country) ->
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
        }
    }
}