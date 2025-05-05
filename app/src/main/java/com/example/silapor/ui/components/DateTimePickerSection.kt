package com.example.silapor.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DateTimePickerSection(
    selectedDate: String,
    selectedTimeStart: String,
    selectedTimeEnd: String,
    onDateClick: () -> Unit,
    onTimeStartClick: () -> Unit,
    onTimeEndClick: () -> Unit
) {
    OutlinedButton(
        onClick = onDateClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Text(
            selectedDate,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedButton(
        onClick = onTimeStartClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Text(
            selectedTimeStart,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedButton(
        onClick = onTimeEndClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Text(
            selectedTimeEnd,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
