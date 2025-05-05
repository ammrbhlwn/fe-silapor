package com.example.silapor.ui.screen.booking

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.silapor.data.remote.response.DataFieldDetail
import com.example.silapor.di.Injection
import com.example.silapor.ui.ViewModelFactory
import com.example.silapor.ui.common.UiState
import com.example.silapor.ui.components.BookingConfirmationDialog
import com.example.silapor.ui.components.CustomTimePicker
import com.example.silapor.ui.components.DetailImage
import com.example.silapor.ui.components.UploadProofPhotoScreen
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun BookingScreen(
    fieldId: Int,
    viewModel: BookingViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.getFieldDetail(fieldId)

    viewModel.uiState.collectAsState(initial = UiState.Empty).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Text("Loading....")
            }
            is UiState.Success -> {
                BookingContent(
                    field = uiState.data.data,
                    onBackClick = navigateBack,
                    viewModel = viewModel
                )
            }
            is UiState.Error -> {
                Text("Terjadi kesalahan: ${uiState.error}")
            }
            is UiState.Empty -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingContent(
    field: DataFieldDetail,
    onBackClick: () -> Unit,
    viewModel: BookingViewModel,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showBuktiPembayaran by remember { mutableStateOf(false) }
    var nama by remember { mutableStateOf("") }
    var nomor by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("Pilih Tanggal") }
    var selectedTimeStart by remember { mutableStateOf("Jam Mulai") }
    var selectedTimeEnd by remember { mutableStateOf("Jam Selesai") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var file by remember { mutableStateOf<File?>(null) }

    val bookingResponse = viewModel.bookingResponse.collectAsState().value
    val totalHarga = viewModel.totalHarga.collectAsState().value

    LaunchedEffect(bookingResponse) {
        if (bookingResponse is UiState.Success) {
            showConfirmationDialog = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        DetailImage(
            fieldImage = field.foto,
            fieldName = field.nama,
            onBackClick = onBackClick
        )

        Text(
            text = "Formulir Booking",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier =
                Modifier.
                fillMaxWidth()
        )

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama Penyewa") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nomor,
            onValueChange = { nomor = it },
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(selectedDate, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(selectedTimeStart, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(selectedTimeEnd, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.checkTotalPrice(field.id, selectedTimeStart, selectedTimeEnd) },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D65A8))
        ) {
            Text(
                "Cek Harga",
                fontSize = 16.sp,
                color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total Harga :", fontSize = 16.sp, fontWeight = FontWeight.Bold)

            when (totalHarga) {
                is UiState.Loading -> {
                    Text("-", fontSize = 16.sp)
                }
                is UiState.Success -> {
                    val total = totalHarga.data.data.totalHarga
                    Text(
                        "Rp $total",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                is UiState.Error -> {
                    Text("Gagal cek harga", color = Color.Red)
                }
                is UiState.Empty -> {}
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Bukti pembayaran:",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { showBuktiPembayaran = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(
                selectedImageUri?.lastPathSegment ?: "Pilih Gambar",
                color = Color.Black
            )
        }

        selectedImageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = "Bukti Pembayaran",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            file = uriToFile(it, LocalContext.current)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (file != null) {
                    viewModel.createBooking(
                        field.id, nama, nomor, selectedDate,
                        selectedTimeStart, selectedTimeEnd, file!!
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D65A8))
        ) {
            Text("Booking Sekarang", fontSize = 16.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (bookingResponse) {
            is UiState.Loading -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Text(
                    text = bookingResponse.error,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            else -> {}
        }
    }

    if (showDatePicker) {
        val state = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let {
                        val date = Date(it)
                        selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                        showDatePicker = false
                    }
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = state)
        }
    }

    if (showTimePicker) {
        CustomTimePicker(
            onTimeSelected = {
                if (selectedTimeStart == "Jam Mulai") selectedTimeStart = it
                else selectedTimeEnd = it
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }

    if (showConfirmationDialog && bookingResponse is UiState.Success) {
        BookingConfirmationDialog(
            bookingCode = bookingResponse.data.data.bookingTrxId,
            onDismiss = { showConfirmationDialog = false }
        )
    }

    if (showBuktiPembayaran) {
        UploadProofPhotoScreen(
            onImageSelected = {
                selectedImageUri = it
                showBuktiPembayaran = false
            }
        )
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