package com.mimm.tehtt.models

import java.time.LocalDate


var Timetable = listOf<List<Lesson>>()

fun getTodayDay() : List<Lesson>{
    val today = Timetable[LocalDate.now().dayOfWeek.value - 1]
    return today
}