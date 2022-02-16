package de.writer_chris.datepicker.datepickerbasic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.util.*

@Composable
fun DayGridSelector(
    modifier: Modifier = Modifier,
    isScrollable: Boolean = true,
    columnCount: Int = 2,
    list: List<DayOfMonthItem>,
    itemModifier: Modifier = Modifier,
    itemColors: List<Color> = listOf(
        MaterialTheme.colors.onBackground,
        MaterialTheme.colors.onPrimary,
        MaterialTheme.colors.secondary
    ),
    callback: (Calendar) -> Unit,
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
                            GridDayOfMonthItem(
                                modifier = itemModifier.weight(1f),
                                data = list[createdItems],
                                itemColors = itemColors,
                                onValueChange = { callback(it) },
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
fun GridDayOfMonthItem(
    modifier: Modifier,
    data: DayOfMonthItem,
    itemColors: List<Color>,
    onValueChange: (Calendar) -> Unit
) {
    val itemModifier =
        if (data.isClickable) modifier.clickable {
            val cal = Calendar.getInstance()
            cal.set(data.year, data.month, data.day)
            onValueChange(cal)
        } else modifier

    Column(
        modifier = itemModifier
            .aspectRatio(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        if (data.isSelected) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = itemColors[2], shape = CircleShape)
            )
            {
                Column(
                    Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = data.day.toString(),
                        color = itemColors[1]
                    )
                }
            }
        }
        Text(
            text = data.day.toString(),
            color = itemColors[0]
        )
    }
}