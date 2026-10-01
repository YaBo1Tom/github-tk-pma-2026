package com.example.a003dicethrowkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a003dicethrowkotlin.ui.theme._003DiceThrowKotlinTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Symbols for the six dice faces
private val diceFaces = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")

// How many random faces are shown before the final result, and the delay between them
private const val rollSteps = 10
private const val rollDelayMs = 250L

// Main color used for the title and dice face, matching the XML + Kotlin version
private val diceColor = Color(0xFF352060)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _003DiceThrowKotlinTheme {
                // Scaffold's innerPadding keeps content clear of system bars (status bar, navigation bar)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    DiceScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DiceScreen(modifier: Modifier = Modifier) {
    // Currently shown dice face and whether a roll animation is in progress
    var diceFace by remember { mutableStateOf(diceFaces.last()) }
    var isRolling by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Screen title
        Text(
            text = "Hoď kostkou",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = diceColor
        )

        // Large dice face symbol, updated during the roll animation
        Text(
            text = diceFace,
            fontSize = 120.sp,
            fontWeight = FontWeight.Bold,
            color = diceColor,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )

        // Starts the roll animation; disabled while it is running
        Button(
            enabled = !isRolling,
            onClick = {
                coroutineScope.launch {
                    isRolling = true
                    rollDice { face -> diceFace = face }
                    isRolling = false
                }
            }
        ) {
            Text(text = "Hodit", fontSize = 28.sp)
        }
    }
}

// Shows a quick series of random dice faces, then after one more delay, the final result
private suspend fun rollDice(onFaceChanged: (String) -> Unit) {
    repeat(rollSteps) {
        delay(rollDelayMs)
        onFaceChanged(diceFaces.random())
    }
    // One more delay, then the final result
    delay(rollDelayMs)
    onFaceChanged(diceFaces.random())
}

@Preview(showBackground = true)
@Composable
fun DiceScreenPreview() {
    _003DiceThrowKotlinTheme {
        DiceScreen()
    }
}
