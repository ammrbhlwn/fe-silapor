package com.example.silapor.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomTimePicker(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var hour by remember { mutableStateOf(8) }
    var minute = 0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pilih Waktu") },
        text = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { if (hour > 0) hour-- }) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Kurangi jam")
                    }
                    Text(
                        text = String.format("%02d:%02d", hour, minute),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    IconButton(onClick = { if (hour < 23) hour++ }) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Tambah jam")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val timeFormatted = String.format("%02d:%02d", hour, minute)
                    onTimeSelected(timeFormatted)
                }
            ) {
                Text("Pilih")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}