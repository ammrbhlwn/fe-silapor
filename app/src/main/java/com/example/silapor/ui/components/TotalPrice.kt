package com.example.silapor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.silapor.data.remote.response.CheckTotalResponse
import com.example.silapor.ui.common.UiState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TotalPrice(
    totalHarga: UiState<CheckTotalResponse>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Total Harga :", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        when (totalHarga) {
            is UiState.Loading -> Text("menghitung...")
            is UiState.Success -> {
                val harga = totalHarga.data.data.totalHarga
                val formattedHarga = NumberFormat
                    .getNumberInstance(Locale("id", "ID"))
                    .format(harga)
                Text(
                    "Rp $formattedHarga",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            is UiState.Error -> Text("Gagal menghitung harga", color = Color.Red)
            is UiState.Empty -> Text("-", fontSize = 16.sp)
        }
    }
}
