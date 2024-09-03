package com.mimm.tehtt.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

val BellRings =
    listOf(
        LocalTime.of(8, 0), LocalTime.of(9, 30),
        LocalTime.of(9, 40), LocalTime.of(11, 10),
        LocalTime.of(11, 50), LocalTime.of(13, 20),
        LocalTime.of(13, 40), LocalTime.of(15, 10),
        LocalTime.of(15, 20), LocalTime.of(16, 50),
        LocalTime.of(17, 0), LocalTime.of(18, 30)
    )

val SaturdayBellRings = listOf(
    LocalTime.of(8, 0), LocalTime.of(9, 30),
    LocalTime.of(9, 40), LocalTime.of(11, 10),
    LocalTime.of(11, 30), LocalTime.of(13, 0),
    LocalTime.of(13, 10), LocalTime.of(14, 40),
    LocalTime.of(14, 50), LocalTime.of(16, 20),
    LocalTime.of(16, 30), LocalTime.of(18, 0)
)

fun getPeriodOfLesson(number: Int): Pair<LocalTime, LocalTime> {
    if (LocalDate.now().dayOfWeek != DayOfWeek.SATURDAY)
        return Pair(BellRings[number * 2 - 2], BellRings[number * 2 - 1])
    return Pair(SaturdayBellRings[number * 2 - 2], SaturdayBellRings[number * 2 - 1])
}

fun getPeriodOfBreak(number: Int): Pair<LocalTime, LocalTime> {
    if (LocalDate.now().dayOfWeek != DayOfWeek.SATURDAY)
        return Pair(BellRings[number * 2 - 1], BellRings[number * 2])
    return Pair(SaturdayBellRings[number * 2 - 1], SaturdayBellRings[number * 2])
}