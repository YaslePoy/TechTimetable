package com.mimm.tehtt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mimm.tehtt.ui.theme.TehTTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainView()
        }
    }
}

@Composable
fun MainView() {
    val navController = rememberNavController()

    TehTTTheme {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(8.dp)) {
                    Spacer(Modifier.weight(1f))

                    Row(modifier = Modifier
                        .background(Color.Gray.copy(0.5f), RoundedCornerShape(16.dp))
                        .padding(6.dp, 3.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Следующий день", fontSize = 13.sp)
                        Image(imageVector = ImageVector.vectorResource(id = R.drawable.arrow_forward),
                            contentDescription = "", modifier = Modifier.size(16.dp))
                    }

            }
        }){
            innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(navController = navController, startDestination = "today") {
                    composable("today") {
                        TodayView()
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