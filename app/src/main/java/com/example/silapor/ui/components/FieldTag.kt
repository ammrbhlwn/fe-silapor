package com.example.silapor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.silapor.ui.theme.SilaporTheme
import com.example.silapor.ui.theme.OrangeRed
import com.example.silapor.ui.theme.White

@Composable
fun FieldTag(
    fieldPrice: Int,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        //field price
        Text(
            text = "$fieldPrice",
            color = White,
            modifier = modifier
                .background(OrangeRed, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 20.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FieldTagPreview() {
    SilaporTheme {
        FieldTag(
            fieldPrice = 250000,
        )
    }
}