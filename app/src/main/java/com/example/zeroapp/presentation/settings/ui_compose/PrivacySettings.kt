package com.example.zeroapp.presentation.settings.ui_compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.zeroapp.R

@Composable
fun PrivacySettings(
    modifier: Modifier = Modifier,
    isCheckedPinCode: Boolean,
    checkChangePinCode: (Boolean) -> Unit,
    isShowBiometricSetting: Boolean,
    isCheckedBiometric: Boolean,
    checkChangeBiometric: (Boolean) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Text(
            modifier = modifier,
            text = stringResource(id = R.string.settings_privacy_text),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        SettingItem(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_normal_5),
                end = dimensionResource(id = R.dimen.padding_normal_3)
            ),
            titleId = R.string.enable_pin_code,
            descriptionId = R.string.enable_pin_code_desc,
            isChecked = isCheckedPinCode,
            checkChanged = checkChangePinCode
        )

        if (isShowBiometricSetting) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
            SettingItem(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_normal_5),
                    end = dimensionResource(id = R.dimen.padding_normal_3)
                ),
                titleId = R.string.enable_biometric_authentication,
                descriptionId = R.string.enable_biometric_authentication_desc,
                isChecked = isCheckedBiometric,
                checkChanged = checkChangeBiometric
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        Divider(modifier = modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.onSecondary)

    }
}