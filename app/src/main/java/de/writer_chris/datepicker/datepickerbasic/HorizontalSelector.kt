package de.writer_chris.datepicker.datepickerbasic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import de.writer_chris.datepicker.R


@Composable
fun <T> HorizontalSelector(
    modifier: Modifier = Modifier,
    title: T,
    callback: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row {
            IconButton(modifier = Modifier.weight(1f), onClick = { callback(-1) }) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(
                        id = R.string.previous
                    )
                )
            }

            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                color = MaterialTheme.colors.onPrimary,
                text = title.toString(),
                textAlign = TextAlign.Center
            )
            IconButton(modifier = Modifier.weight(1f), onClick = { callback(1) }) {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(
                        id = R.string.next
                    )
                )
            }
        }
    }
}
/**Example Code
// =============
@Preview
@Composable
fun HorizontalSelectorPreview() {
YourAppTheme {
var lastValue by remember {
mutableStateOf(0)
}
HorizontalSelector(title = lastValue, callback = { lastValue = it })
}
}
 **/