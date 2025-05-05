package com.example.silapor.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImagePreview(uri: Uri) {
    AsyncImage(
        model = uri,
        contentDescription = "Preview Foto",
        modifier = Modifier
            .padding(8.dp),
        contentScale = ContentScale.Crop
    )
}