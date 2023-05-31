package antuere.how_are_you.presentation.screens.cats.ui_compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import antuere.domain.dto.ImageSource
import antuere.how_are_you.R
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ImageSourcesSelector(
    modifier: Modifier = Modifier,
    value: ImageSource,
    imageSources: ImmutableList<ImageSource>,
    onImageSourceChanged: (ImageSource) -> Unit
) {
    Column(modifier = modifier.selectableGroup()) {
        imageSources.forEach { source ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(MaterialTheme.shapes.large)
                    .selectable(
                        selected = (source.name == value.name),
                        onClick = { onImageSourceChanged(source) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_0)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (source.name == value.name),
                    onClick = null
                )
                Text(
                    text = source.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_normal_0))
                )
            }
        }

    }
}