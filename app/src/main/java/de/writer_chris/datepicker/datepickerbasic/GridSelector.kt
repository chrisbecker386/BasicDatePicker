package de.writer_chris.datepicker.datepickerbasic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle


@Composable
fun <T> GridSelector(
    modifier: Modifier = Modifier,
    isScrollable: Boolean = true,
    isClickable: Boolean = true,
    columnCount: Int = 2,
    list: List<T>,
    itemModifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(),
    callback: (T) -> Unit,
) {
    val scrollState = rememberScrollState()
    val selectorModifier = if (isScrollable) modifier.verticalScroll(scrollState) else modifier
    Column(selectorModifier) {
        val itemCount = list.size
        val rows = (itemCount / columnCount) + (if (itemCount % columnCount == 0) 0 else 1)
        Column {
            var createdItems = 0
            for (i in 0 until rows) {
                Row(
                    Modifier.fillMaxWidth(
                        if (i == rows - 1) {
                            ((itemCount - createdItems).toFloat() / columnCount.toFloat())
                        } else 1f
                    )
                ) {
                    for (ii in 0 until columnCount) {
                        if (createdItems + 1 <= itemCount) {
                            GridItem(
                                modifier = itemModifier.weight(1f),
                                isClickable = isClickable,
                                itemNumber = createdItems,
                                onValueChange = { callback(it) },
                                dataModel = list[createdItems],
                                itemTextStyle = itemTextStyle
                            )
                            createdItems += 1
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> GridItem(
    modifier: Modifier,
    isClickable: Boolean,
    itemNumber: Int,
    dataModel: T,
    itemTextStyle: TextStyle,
    onValueChange: (T) -> Unit
) {
    val itemModifier =
        if (isClickable) modifier.clickable { onValueChange(dataModel) } else modifier

    Column(
        modifier = itemModifier
            .aspectRatio(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        /** implement your dataModel **/
        Text(
            text = dataModel.toString(),
            style = itemTextStyle
        )

    }
}

/** Example Code
// =============
@Preview
@Composable
fun GridSelectorPreview() {
YourAppTheme {
var lastClicked by remember {
mutableStateOf(-1)
}
Column(Modifier.background(Color.LightGray)) {
Text(text = lastClicked.toString())
GridSelector(
columnCount = 2,
list = listOf(1, 2, 3),
callback = { lastClicked = it }
)
}
}
}
 **/

