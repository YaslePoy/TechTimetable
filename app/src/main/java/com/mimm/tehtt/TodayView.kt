package com.mimm.tehtt

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mimm.tehtt.models.Lesson
import com.mimm.tehtt.models.getPeriodOfBreak
import com.mimm.tehtt.models.getPeriodOfLesson
import com.mimm.tehtt.models.getTodayDay
import com.mimm.tehtt.ui.theme.TehTTTheme
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TodayView() {
    Column(Modifier.fillMaxSize()) {
        val todays = getTodayDay()
        Text(text = LocalDate.now().format(DateTimeFormatter.ofPattern( "d MMMM")), fontSize = 24.sp, modifier = Modifier.padding(24.dp, 16.dp).fillMaxWidth(), textAlign = TextAlign.Center)
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(24.dp, 16.dp)
        ) {
            items(todays) { lesson: Lesson ->
                LessonLine(lesson = lesson, modifier = Modifier.padding(0.dp, 8.dp))
                if (todays.last() != lesson) {
                    BreakLine(number = lesson.number, modifier = Modifier.padding(0.dp, 8.dp))
                }
            }
        }
    }
}

@Composable
fun LessonLine(modifier: Modifier = Modifier, lesson: Lesson, timeline: Boolean = true) {
    Card(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = lesson.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
            )
            Row(Modifier.fillMaxWidth()) {
                Text(text = lesson.room)
                Text(
                    text = lesson.teacher,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
            if (timeline)
                Timeline(times = getPeriodOfLesson(lesson.number))
        }
    }
}

@Composable
fun BreakLine(modifier: Modifier = Modifier, number: Int) {
    Card(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Перемена",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
            )
            Timeline(times = getPeriodOfBreak(number))
        }
    }
}

@Composable
fun Timeline(modifier: Modifier = Modifier, times: Pair<LocalTime, LocalTime>) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = times.first.format(DateTimeFormatter.ofPattern("HH:mm")))
        var currentProgress by remember { mutableFloatStateOf(0f) }
        val now = LocalTime.now()
        LaunchedEffect(key1 = currentProgress) {
            if (now.isAfter(times.first) && now.isBefore(times.second)) {
                val totalDelta = times.second.toSecondOfDay() - times.first.toSecondOfDay()
                while (LocalTime.now().toSecondOfDay() < times.second.toSecondOfDay()) {
                    currentProgress = ((LocalTime.now()
                        .toSecondOfDay() - times.first.toSecondOfDay()).toFloat() / totalDelta.toFloat())
                    Log.i("progress", "Timeline: $currentProgress")
                    delay(250)
                }
            }
        }

        LinearProgressIndicator(
            progress = { currentProgress },
            Modifier
                .weight(1f)
                .padding(4.dp),
            trackColor = Color.Gray
        )
        Text(text = times.second.format(DateTimeFormatter.ofPattern("HH:mm")))
    }
}

@Preview
@Composable
private fun TodayPreview() {
    TehTTTheme {
        TodayView()
    }
}

@Preview
@Composable
private fun LessonPreview() {
    TehTTTheme {
        LessonLine(
            lesson = Lesson(
                "МДК.11.01 Технология разработки и защиты баз данных",
                "407/409",
                "Волчкова К.С. / Моисеева А.Н.",
                5
            )
        )
    }
}

@Preview
@Composable
private fun BreakPreview() {
    TehTTTheme {
        BreakLine(number = 1)
    }
}