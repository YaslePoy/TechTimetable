package com.mimm.tehtt

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimm.tehtt.models.Lesson
import com.mimm.tehtt.models.Timetable
import com.mimm.tehtt.ui.theme.TehTTTheme

@Composable
fun NextDayView(weekDay: Int) {
    val lessons = Timetable[weekDay]
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(24.dp, 16.dp)
    ) {
        items(lessons) { lesson: Lesson ->
            LessonLine(lesson = lesson, modifier = Modifier.padding(0.dp, 8.dp), timeline = false)
        }
    }
}

@Preview
@Composable
private fun NextDayPrev() {
    TehTTTheme {
        NextDayView(2)
    }
}