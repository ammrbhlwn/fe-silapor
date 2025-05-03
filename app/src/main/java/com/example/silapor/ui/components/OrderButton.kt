package com.example.silapor.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.silapor.ui.theme.White

@Composable
fun OrderButton(
    fieldName: String,
    fieldPrice: Int,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    //CTA Button
    Button(
        onClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Sewa sekarang juga!")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "$fieldName $fieldPrice!"
                )
            }
            context.startActivity(
                Intent.createChooser(shareIntent, "Share via")
            )
        }, colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF4500)
        ), modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Booking",
            fontSize = 18.sp,
            color = White
        )
    }
}
