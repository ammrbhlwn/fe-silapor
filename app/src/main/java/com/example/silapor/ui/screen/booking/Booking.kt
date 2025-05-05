package com.example.silapor.ui.screen.booking

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.silapor.data.remote.response.DataFieldDetail
import com.example.silapor.di.Injection
import com.example.silapor.ui.ViewModelFactory
import com.example.silapor.ui.common.UiState
import com.example.silapor.ui.components.BookingConfirmationDialog
import com.example.silapor.ui.components.BookingInputFields
import com.example.silapor.ui.components.CustomTimePicker
import com.example.silapor.ui.components.DateTimePickerSection
import com.example.silapor.ui.components.LoaderAnimation
import com.example.silapor.ui.components.TotalPrice
import com.example.silapor.ui.components.UploadImageSection
import com.example.silapor.ui.components.UploadProofPhotoScreen
import com.example.silapor.ui.theme.BluePrimary
import java.io.File
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
    LaunchedEffect(fieldId) {
        viewModel.getFieldDetail(fieldId)
    }

    viewModel.uiState.collectAsState(initial = UiState.Empty).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoaderAnimation()
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

enum class TimeTarget {
    START, END
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

    var editingTimeField by remember { mutableStateOf("") }
    var timePickerTarget by remember { mutableStateOf(TimeTarget.START) }

    val context = LocalContext.current
    val bookingResponse = viewModel.bookingResponse.collectAsState().value
    val totalHarga = viewModel.totalHarga.collectAsState().value

    val isFormValid = nama.isNotBlank() &&
            nomor.isNotBlank() &&
            selectedDate != "Pilih Tanggal" &&
            selectedTimeStart != "Jam Mulai" &&
            selectedTimeEnd != "Jam Selesai" &&
            selectedImageUri != null

    val (initialHour) = when (editingTimeField) {
        "start" -> selectedTimeStart.takeIf { it != "Jam Mulai" }?.split(":")?.let {
            it[0].toInt() to it[1].toInt()
        } ?: (8 to 0)

        "end" -> selectedTimeEnd.takeIf { it != "Jam Selesai" }?.split(":")?.let {
            it[0].toInt() to it[1].toInt()
        } ?: (8 to 0)

        else -> 8 to 0
    }

    LaunchedEffect(bookingResponse) {
        if (bookingResponse is UiState.Success) {
            showConfirmationDialog = true
        } else if (bookingResponse is UiState.Error) {
            Toast.makeText(context, "Gagal booking", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            model = field.foto,
            contentDescription = field.nama,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Formulir Booking",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier =
                Modifier.
                fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        BookingInputFields(
            nama = nama,
            onNamaChange = { nama = it },
            nomor = nomor,
            onNomorChange = { nomor = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DateTimePickerSection(
            selectedDate = selectedDate,
            selectedTimeStart = selectedTimeStart,
            selectedTimeEnd = selectedTimeEnd,
            onDateClick = {
                showDatePicker = true
            },
            onTimeStartClick = {
                timePickerTarget = TimeTarget.START
                editingTimeField = "start"
                showTimePicker = true
            },
            onTimeEndClick = {
                timePickerTarget = TimeTarget.END
                editingTimeField = "end"
                showTimePicker = true
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.checkTotalPrice(field.id, selectedTimeStart, selectedTimeEnd) },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
        ) {
            Text(
                "Cek Harga",
                fontSize = 16.sp,
                color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TotalPrice(
            totalHarga = totalHarga
        )

        Spacer(modifier = Modifier.height(24.dp))

        UploadImageSection(
            selectedImageUri = selectedImageUri,
            onSelectImage = {
                showBuktiPembayaran = true
            },
            onFileCreated = {
                file = it
            }
        )

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
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
        ) {
            Text("Booking Sekarang", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (bookingResponse) {
            is UiState.Loading -> {
                LoaderAnimation()
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

    Box(
        modifier = Modifier
            .padding(24.dp)
            .background(
                Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .zIndex(1f)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .clickable { onBackClick() }
        )
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
            initialHour = initialHour,
            onTimeSelected = {
                if (editingTimeField == "start") {
                    selectedTimeStart = it
                } else if (editingTimeField == "end") {
                    selectedTimeEnd = it
                }
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }

    if (showConfirmationDialog && bookingResponse is UiState.Success) {
        BookingConfirmationDialog(
            bookingCode = bookingResponse.data.data.bookingTrxId,
            onDismiss = {
                showConfirmationDialog = false
                onBackClick()
            }
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