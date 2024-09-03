package com.mimm.tehtt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mimm.tehtt.models.Lesson
import com.mimm.tehtt.models.Timetable
import com.mimm.tehtt.ui.theme.TehTTTheme
import kotlinx.serialization.json.Json
import java.time.DayOfWeek
import java.time.LocalDate


var forwardDays = 0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }
    }
}

@Composable
fun MainView() {
    val context = LocalContext.current
    val json = context.resources.openRawResource(R.raw.timetable).bufferedReader().readText()

    Timetable = Json.decodeFromString<Array<Array<Lesson>>>(json)

    val navController = rememberNavController()

    TehTTTheme {

        var enableBackToday by remember {
            mutableStateOf(false)
        }

        Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                if (enableBackToday)
                    Row(
                        modifier = Modifier
                            .background(Color.Gray.copy(0.5f), RoundedCornerShape(16.dp))
                            .padding(6.dp, 3.dp)
                            .clickable {
                                forwardDays++
                                enableBackToday = false
                                navController.popBackStack("today", false)
                            }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                            contentDescription = "", modifier = Modifier.size(16.dp)
                        )
                        Text(text = "Следующий день", fontSize = 13.sp)

                    }
                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .background(Color.Gray.copy(0.5f), RoundedCornerShape(16.dp))
                        .padding(6.dp, 3.dp)
                        .clickable {
                            forwardDays++
                            navController.navigate("forward/$forwardDays")
                            enableBackToday = true
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Следующий день", fontSize = 13.sp)
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_forward),
                        contentDescription = "", modifier = Modifier.size(16.dp)
                    )
                }

            }
        }) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(navController = navController, startDestination = "today") {
                    composable("today") {
                        TodayView()
                    }
                    composable("forward/{count}") { backStackEntry ->
                        val forward = backStackEntry.arguments!!.getInt("count")
                        val now = LocalDate.now()
                        for (i in 0..forward) {
                            now.plusDays(1)
                            if (now.dayOfWeek == DayOfWeek.SUNDAY)
                                now.plusDays(1)
                        }

                        val weekDay = now.dayOfWeek.ordinal
                        NextDayView(weekDay = weekDay)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainView()
}