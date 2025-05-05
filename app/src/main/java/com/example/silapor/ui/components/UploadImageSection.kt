package com.example.silapor.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.io.FileOutputStream

@Composable
fun UploadImageSection(
    selectedImageUri: Uri?,
    onSelectImage: () -> Unit,
    onFileCreated: (File) -> Unit
) {
    val context = LocalContext.current

    Text(
        text = "Bukti pembayaran:",
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedButton(
        onClick = onSelectImage,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Text(selectedImageUri?.lastPathSegment ?: "Pilih Gambar",
            color = MaterialTheme.colorScheme.onBackground)
    }

    selectedImageUri?.let { uri ->
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = "Bukti Pembayaran",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        val file = uriToFile(uri, context)
        onFileCreated(file)
    }
}

fun uriToFile(uri: Uri, context: Context): File {
    val contentResolver = context.contentResolver
    val file = File.createTempFile("temp_image", null, context.cacheDir)

    val inputStream = contentResolver.openInputStream(uri)
    val outputStream = FileOutputStream(file)

    inputStream?.copyTo(outputStream)

    inputStream?.close()
    outputStream.close()

    return file
}
