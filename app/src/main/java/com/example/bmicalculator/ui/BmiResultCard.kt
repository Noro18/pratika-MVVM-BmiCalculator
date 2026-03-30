package com.example.bmicalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmicalculator.model.BmiData

@Composable
fun BmiResultCard(result: BmiData) {
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
                    text = "%.1f".format(result.bmi),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = result.status.uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = result.color
                    )
                )
            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(result.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = null,
                    tint = result.color,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

@Composable
fun BmiScale(bmi: Float) {
    val segments = listOf(
        Color(0xFF4FC3F7),
        Color(0xFF66BB6A),
        Color(0xFFFFA726),
        Color(0xFFEF5350)
    )
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "BMI Scale",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Brush.horizontalGradient(colors = segments))
            )
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
                    modifier = Modifier.size(32.dp).offset(y = (-18).dp),
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





@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ResultCardPreview() {
    val myData = BmiData(
        bmi = 22.5f,
        status = "Normal",
        color = Color.Green
    )

    BmiResultCard(result = myData)
    BmiScale(myData.bmi)
}
