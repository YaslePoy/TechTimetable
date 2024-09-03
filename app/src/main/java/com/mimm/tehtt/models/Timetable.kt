package com.mimm.tehtt.models

import java.time.LocalDate


var Timetable = arrayOf<Array<Lesson>>()

fun getTodayDay(): Array<Lesson> {
    if (Timetable.isEmpty())
        return arrayOf<Lesson>()
    val today = Timetable[LocalDate.now().dayOfWeek.value - 1]
    return today
}