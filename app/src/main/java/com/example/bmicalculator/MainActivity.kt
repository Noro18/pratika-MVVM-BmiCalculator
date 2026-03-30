package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.MonitorWeight
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Straighten
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmicalculator.ui.BmiScreen
// import com.example.bmicalculator.ui.BmiScreen
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        BmiScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun BmiApp() {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmiValue by remember { mutableStateOf<Float?>(null) }
    var showResult by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // App Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "BMI Calculator",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = "Calculate your body mass index",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Input Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") },
                    leadingIcon = { Icon(Icons.Rounded.MonitorWeight, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text("Height (cm)") },
                    leadingIcon = { Icon(Icons.Rounded.Straighten, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            bmiValue = calculateBMI(weight, height)
                            showResult = bmiValue != null
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Calculate", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            weight = ""
                            height = ""
                            bmiValue = null
                            showResult = false
                        },
                        modifier = Modifier.height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Rounded.Refresh, contentDescription = "Reset")
                    }
                }
            }
        }

        // Results Section
        AnimatedVisibility(
            visible = showResult,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it / 2 }),
            exit = shrinkVertically()
        ) {
            bmiValue?.let { bmi ->
                val status = getBmiStatus(bmi)
                val color = getBmiColor(status)

                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(28.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Your Result",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = "%.1f".format(bmi),
                                    style = MaterialTheme.typography.displayLarge.copy(
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                Text(
                                    text = status.uppercase(),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = color
                                    )
                                )
                            }
                            
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(color.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Person,
                                    contentDescription = null,
                                    tint = color,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                    }

                    BmiScale(bmi)
                }
            }
        }
    }
}

@Composable
fun BmiScale(bmi: Float) {
    val segments = listOf(
        "Underweight" to Color(0xFF4FC3F7), // Light Blue
        "Normal"      to Color(0xFF66BB6A), // Green
        "Overweight"  to Color(0xFFFFA726), // Orange
        "Obese"       to Color(0xFFEF5350)  // Red
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "BMI Scale",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            // Background Scale
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        Brush.horizontalGradient(
                            colors = segments.map { it.second }
                        )
                    )
            )

            // Marker
            val progress = ((bmi - 15) / (40 - 15)).coerceIn(0f, 1f)
            Column(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .align(Alignment.TopStart),
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .offset(y = (-18).dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("15", "18.5", "25", "30", "40").forEach {
                Text(it, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }
    }
}

fun calculateBMI(weight: String, height: String): Float? {
    val w = weight.toFloatOrNull()
    val h = height.toFloatOrNull()
    if (w == null || h == null || h <= 0f) return null
    return w / ((h / 100) * (h / 100))
}

fun getBmiStatus(bmi: Float): String = when {
    bmi < 18.5 -> "Underweight"
    bmi < 25.0 -> "Normal"
    bmi < 30.0 -> "Overweight"
    else -> "Obese"
}

fun getBmiColor(status: String): Color = when (status) {
    "Underweight" -> Color(0xFF4FC3F7)
    "Normal"      -> Color(0xFF66BB6A)
    "Overweight"  -> Color(0xFFFFA726)
    "Obese"       -> Color(0xFFEF5350)
    else          -> Color.Gray
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BmiPreview() {
    BMICalculatorTheme {
        BmiScreen()
    }
}