package com.asser.agecalculator.model

data class AgeResult(
    val years: Int,
    val months: Int,
    val days: Int,
    val totalDays: Long,
    val totalHours: Long,
    val totalMinutes: Long
)
