package com.example.silapor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.silapor.ui.theme.SilaporTheme

@Composable
fun DetailImage(
    fieldImage: String,
    fieldName: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        //image
        AsyncImage(
            model = fieldImage,
            contentDescription = fieldName,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        //icon back
        Box(
            modifier = modifier
                .padding(24.dp)
                .background(LightGray, RoundedCornerShape(8.dp))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "icon",
                tint = Color.Black,
                modifier = modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clickable { onBackClick() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailImagePreview() {
    SilaporTheme {
        DetailImage(
            fieldImage = "https://jynmzifknyokgtbzddsr.supabase.co/storage/v1/object/public/imageupload/lapangan/1/68124e4eb1f16.png",
            fieldName = "Sushi Roll",
            onBackClick = {}
        )
    }
}