package com.example.silapor.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.silapor.ui.theme.DarkGray
import com.example.silapor.ui.theme.SilaporTheme

@Composable
fun FieldInfo(
    fieldPhoto: String,
    fieldName: String,
    fieldAddress: String,
    modifier: Modifier = Modifier
) {
    Row {
        //resto image
        AsyncImage(
            model = fieldPhoto,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = modifier.width(16.dp))

        //resto detail
        Column {
            //resto name
            Text(
                text = fieldName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = modifier.height(12.dp))

            //resto address
            Text(
                text = fieldAddress,
                fontSize = 20.sp,
                color = DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FieldInfoPreview() {
    SilaporTheme {
        FieldInfo(
            fieldName = "Sushi Hiro",
            fieldPhoto = "https://jynmzifknyokgtbzddsr.supabase.co/storage/v1/object/public/imageupload/lapangan/1/68124e4eb1f16.png",
            fieldAddress = "Jl. HOS. Cokroaminoto No.63, RT.003/RW.009, Gondangdia, East Karang, Kota Tangerang, Banten 10350"
        )
    }
}