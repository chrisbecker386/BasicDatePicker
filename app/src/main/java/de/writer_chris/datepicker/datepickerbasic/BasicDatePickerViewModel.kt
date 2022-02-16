package de.writer_chris.datepicker.datepickerbasic

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

const val LOG = "BasicDatePickerViewModel"

class BasicDatePickerViewModel : ViewModel() {
    @SuppressLint("SimpleDateFormat")
    private val sdfFull = SimpleDateFormat("dd. MMM yyyy")

    @SuppressLint("SimpleDateFormat")
    private val sdfYear = SimpleDateFormat("yyyy")

    @SuppressLint("SimpleDateFormat")
    private val sdfMonthYear = SimpleDateFormat("MMMM yyyy")

    @SuppressLint("SimpleDateFormat")
    private val sdfWeekDayLong = SimpleDateFormat("EEEE")

    @SuppressLint("SimpleDateFormat")
    private val sdfWeekDayShort = SimpleDateFormat("EE")

    private val _selectedDate: MutableLiveData<Calendar> = MutableLiveData(getTodayDate())
    val selectedDateAsString: LiveData<String> = Transformations.map(_selectedDate) { cal ->
        sdfFull.format(cal.timeInMillis).toString()
    }
    private val _navigateDate: MutableLiveData<Calendar> = MutableLiveData(getTodayDate())

    val year: LiveData<String> = Transformations.map(_navigateDate) { cal ->
        sdfYear.format(cal.timeInMillis).toString()
    }

    val month: LiveData<String> = Transformations.map(_navigateDate) { cal ->
        sdfMonthYear.format(cal.timeInMillis).toString()
    }

    val selectedWeekDay: LiveData<String> = Transformations.map(_selectedDate) { cal ->
        sdfWeekDayLong.format(cal.timeInMillis).toString()
    }

    fun getWeekDayList(weekStartsOnMonday: Boolean = true): List<String> {
        val cal = getTodayDate()
        if (weekStartsOnMonday) cal.set(1984, 0, 2) else cal.set(1984, 0, 1)
        val list = mutableListOf<String>()
        repeat(7) {
            list.add(sdfWeekDayShort.format(cal.timeInMillis))
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
        return list
    }

    private fun getTodayDate(): Calendar = Calendar.getInstance()

    fun getMonthArray(weekStartsOnMonday: Boolean = true): List<DayOfMonthItem> {
        val list = mutableListOf<DayOfMonthItem>()

        val cal = Calendar.getInstance()
        cal.timeInMillis = _navigateDate.value?.timeInMillis
            ?: throw IllegalStateException("$LOG manipulateYear value is null")

        cal.set(Calendar.DAY_OF_MONTH, 1)
        val firstWeekDayOfMonth: Int = cal.get(Calendar.DAY_OF_WEEK)

        //it depends from the starting day of the week how the calender will look
        val startOffset = if (weekStartsOnMonday) {
            if (firstWeekDayOfMonth == 1) 6 else (firstWeekDayOfMonth - 2)
        } else {
            firstWeekDayOfMonth - 1
        }

        cal.add(Calendar.MONTH, -1)
        val previousMonthLastDay: Int = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        cal.set(Calendar.DAY_OF_MONTH, previousMonthLastDay - (startOffset))

        for (i in 0 until 42) {
            cal.add(Calendar.DAY_OF_MONTH, 1)

            list.add(
                DayOfMonthItem(
                    year = cal.get(Calendar.YEAR),
                    month = cal.get(Calendar.MONTH),
                    day = cal.get(Calendar.DAY_OF_MONTH),
                    isSelected = (_selectedDate.value!!.get(Calendar.DAY_OF_MONTH) == cal.get(
                        Calendar.DAY_OF_MONTH
                    ) &&
                            _selectedDate.value!!.get(Calendar.MONTH) == cal.get(
                        Calendar.MONTH
                    ) &&
                            _selectedDate.value!!.get(Calendar.YEAR) == cal.get(
                        Calendar.YEAR
                    )),
                    isClickable = true
                )
            )
        }
        return list
    }

    fun manipulateMonth(value: Int) {
        val cal = Calendar.getInstance()
        cal.timeInMillis = _navigateDate.value?.timeInMillis
            ?: throw IllegalStateException("$LOG manipulateYear value is null")
        cal.add(Calendar.MONTH, value)
        _navigateDate.value = cal
    }

    fun selectDate(cal: Calendar) {
        _navigateDate.value = cal
        _selectedDate.value = cal
    }
}