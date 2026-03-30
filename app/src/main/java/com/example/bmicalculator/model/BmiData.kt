package com.example.bmicalculator.model

import androidx.compose.ui.graphics.Color
import com.example.bmicalculator.ui.theme.Underweight
import com.example.bmicalculator.ui.theme.NormalWeight
import com.example.bmicalculator.ui.theme.Overweight
import com.example.bmicalculator.ui.theme.Obese

data class BmiData (
    val bmi: Float,
    val status: String,
    val color: Color
)

fun calculateBMI(weight: String, height: String): Float? {
    val w = weight.toFloatOrNull()
    val h = height.toFloatOrNull()
    if (w == null || h == null || (h / 100) <= 0f) return null
    return w / ((h / 100) * (h / 100))
}

fun getBmiStatus(bmi: Float): String = when {
    bmi < 18.5 -> "Underweight"
    bmi < 25.0 -> "Normal"
    bmi < 30.0 -> "Overweight"
    else       -> "Obese"
}

fun getBmiColor(status: String): Color = when (status) {
    "Underweight" -> Underweight
    "Normal"      -> NormalWeight
    "Overweight"  -> Overweight
    "Obese"       -> Obese
    else          -> Color.Gray
}

fun buildBmiResult (weight: String, height: String): BmiData? {
    val bmi = calculateBMI(weight, height) ?: return null
    val status = getBmiStatus(bmi)
    val color = getBmiColor(status)
    return BmiData(bmi = bmi, status = status, color = color)
}
