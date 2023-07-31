package antuere.how_are_you.presentation.base.ui_compose_components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import timber.log.Timber

class UIDialogListener {
    private var currentDialog by mutableStateOf<UIDialog?>(null)

    fun showDialog(uiDialog: UIDialog) {
        currentDialog = uiDialog
    }

    @Composable
    fun SetupDialogListener() {
        Timber.i("Theme feature: setup dialog")
        currentDialog?.let {
            Dialog(dialog = it, onDismiss = { currentDialog = null })
        }
    }
}