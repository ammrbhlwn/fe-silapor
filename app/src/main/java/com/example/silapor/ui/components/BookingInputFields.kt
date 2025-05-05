package com.example.silapor.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun BookingInputFields(
    nama: String,
    onNamaChange: (String) -> Unit,
    nomor: String,
    onNomorChange: (String) -> Unit)
{
    OutlinedTextField(
        value = nama,
        onValueChange = onNamaChange,
        label = { Text("Nama Penyewa") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = nomor,
        onValueChange = onNomorChange,
        label = { Text("Nomor Telepon") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}
