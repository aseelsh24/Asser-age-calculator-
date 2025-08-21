package com.asser.agecalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asser.agecalculator.model.AgeResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.Period

class MainViewModel : ViewModel() {

    private val _birthDate = MutableStateFlow<LocalDate?>(null)
    val birthDate: StateFlow<LocalDate?> = _birthDate.asStateFlow()

    private val _endDate = MutableStateFlow<LocalDate?>(null)
    val endDate: StateFlow<LocalDate?> = _endDate.asStateFlow()

    private val _ageResult = MutableStateFlow<AgeResult?>(null)
    val ageResult: StateFlow<AgeResult?> = _ageResult.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun setBirthDate(year: Int, month: Int, day: Int) {
        _birthDate.value = LocalDate.of(year, month, day)
    }

    fun setEndDate(year: Int, month: Int, day: Int) {
        _endDate.value = LocalDate.of(year, month, day)
    }

     fun setEndDateToToday() {
        _endDate.value = LocalDate.now()
    }

    fun calculateAge() {
        val birth = _birthDate.value
        val end = _endDate.value

        if (birth == null || end == null) {
            _error.value = "Please select both dates."
            return
        }

        if (end.isBefore(birth)) {
            _error.value = "End date cannot be before birth date."
            return
        }

        viewModelScope.launch {
            val period = Period.between(birth, end)
            val duration = Duration.between(birth.atStartOfDay(), end.atStartOfDay())

            val totalDays = duration.toDays()
            val totalHours = duration.toHours()
            val totalMinutes = duration.toMinutes()

            _ageResult.value = AgeResult(
                years = period.years,
                months = period.months,
                days = period.days,
                totalDays = totalDays,
                totalHours = totalHours,
                totalMinutes = totalMinutes
            )
            _error.value = null
        }
    }

    fun clearError() {
        _error.value = null
    }
}
