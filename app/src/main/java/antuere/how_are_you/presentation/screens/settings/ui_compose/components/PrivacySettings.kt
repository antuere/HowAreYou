package antuere.how_are_you.presentation.screens.settings.ui_compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.divider.DefaultDivider
import antuere.how_are_you.presentation.base.ui_compose_components.settings.SettingItem

@Composable
fun PrivacySettings(
    isCheckedPinCode: Boolean,
    checkChangePinCode: (Boolean) -> Unit,
    isShowBiometricSetting: Boolean,
    isCheckedBiometric: Boolean,
    checkChangeBiometric: (Boolean) -> Unit,
    onClickPrivacyPolicy: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Text(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_normal_2)
            ),
            text = stringResource(id = R.string.settings_privacy_text),
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        SettingItemWithSwitch(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_normal_3),
                end = dimensionResource(id = R.dimen.padding_normal_2)
            ),
            titleId = R.string.enable_pin_code,
            descriptionId = R.string.enable_pin_code_desc,
            isChecked = isCheckedPinCode,
            onCheckedChange = checkChangePinCode,
            iconId = R.drawable.ic_settings_pin
        )

        AnimatedVisibility(visible = isShowBiometricSetting) {
            SettingItemWithSwitch(
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.padding_small_2),
                    start = dimensionResource(id = R.dimen.padding_normal_3),
                    end = dimensionResource(id = R.dimen.padding_normal_2)
                ),
                titleId = R.string.enable_biometric_authentication,
                descriptionId = R.string.enable_biometric_authentication_desc,
                isChecked = isCheckedBiometric,
                onCheckedChange = checkChangeBiometric,
                iconId = R.drawable.ic_finger_print
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        SettingItem(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_normal_3),
                end = dimensionResource(id = R.dimen.padding_normal_2)
            ),
            titleId = R.string.settings_privacy_policy ,
            iconId = R.drawable.ic_settings_privacy_policy ,
            onClick = onClickPrivacyPolicy
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        DefaultDivider(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_normal_2)
            )
        )
    }
}