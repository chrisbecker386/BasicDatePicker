package de.writer_chris.datepicker.datepickerbasic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.writer_chris.datepicker.ui.theme.DatePickerTheme

@Composable
fun BasicDatePicker(
    weekStartsOnMonday: Boolean = true,
    viewModel: BasicDatePickerViewModel = BasicDatePickerViewModel(),
    borderColor: Color = MaterialTheme.colors.primary,
    backgroundColorHeader: Color = MaterialTheme.colors.primary,
    textColorOnHeader: Color = MaterialTheme.colors.onPrimary,
    backGroundColorWeekDays: Color = MaterialTheme.colors.primaryVariant,
    backGroundColorDate: Color = MaterialTheme.colors.background,
    cancelBtnColors: List<Color> = listOf(
        MaterialTheme.colors.onBackground,
        MaterialTheme.colors.primaryVariant
    ), okButtonColors: List<Color> = listOf(
        MaterialTheme.colors.onSecondary,
        MaterialTheme.colors.primary
    )

) {
    val selectedDate by viewModel.selectedDateAsString.observeAsState(initial = "default selected Date")
    val selectedWeekDay by viewModel.selectedWeekDay.observeAsState("weekday")
    val month by viewModel.month.observeAsState("Month")


    Column {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(
                    width = 2.dp, shape = RoundedCornerShape(15.dp),
                    color = borderColor
                ),
            shape = RoundedCornerShape(15.dp),
        ) {
            Column(Modifier.background(color = backgroundColorHeader)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp),
                    text = selectedWeekDay,
                    color = textColorOnHeader,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp

                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    text = selectedDate,
                    color = textColorOnHeader,
                    textAlign = TextAlign.Start,
                    fontSize = 32.sp

                )
//                HorizontalSelector(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    title = year,
//                    callback = { viewModel.manipulateYear(it) })
                HorizontalSelector(modifier = Modifier
                    .fillMaxWidth(),
                    title = month, callback = { viewModel.manipulateMonth(it) })
                GridSelector(
                    Modifier.background(backGroundColorWeekDays),
                    columnCount = 7,
                    list = viewModel.getWeekDayList(weekStartsOnMonday),
                    callback = {},
                    isClickable = false,
                )
                DayGridSelector(
                    Modifier.background(backGroundColorDate),
                    columnCount = 7,
                    list = viewModel.getMonthArray(weekStartsOnMonday),
                    callback = { viewModel.selectDate(it) })

                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(backGroundColorDate)
                        .padding(8.dp)
                ) {
                    Button(modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = cancelBtnColors[0],
                            backgroundColor = cancelBtnColors[1]
                        ),
                        onClick = {
                            /*TODO your implementation here*/
                        }) {
                        Text(text = "cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        modifier = Modifier
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = okButtonColors[0],
                            backgroundColor = okButtonColors[1]
                        ),
                        onClick = {
                            /*TODO your implementation here*/
                        }) {
                        Text(text = "ok")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewBasicDatePicker() {
    DatePickerTheme {
        BasicDatePicker(weekStartsOnMonday = false)
    }
}
