package com.example.silapor.ui.screen.bookingDetail

import android.content.Context
import android.net.Uri
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.silapor.data.remote.response.DataFieldDetail
import com.example.silapor.data.remote.response.JamTersedia
import com.example.silapor.di.Injection
import com.example.silapor.ui.ViewModelFactory
import com.example.silapor.ui.common.UiState
import com.example.silapor.ui.components.DetailImage
import com.example.silapor.ui.theme.SilaporTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun BookingDetailScreen(
    fieldId: Int,
    viewModel: BookingDetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFieldById(fieldId)
            }

            is UiState.Success -> {
                BookingDetailContent(
                    field = uiState.data,
                    onBackClick = navigateBack
                )
            }

            is UiState.Error -> {
                Text(text = uiState.error)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailContent(
    field: DataFieldDetail,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDurationDialog by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showBuktiPembayaran by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Pilih Tanggal") }
    var selectedTime by remember { mutableStateOf("Jam mulai") }
    var selectedDuration by remember { mutableStateOf(1) }

    DetailImage(
        fieldImage = field.foto,
        fieldName = field.nama,
        onBackClick = onBackClick
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rincian",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Pilih tanggal")

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text("Pilih Tanggal", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Jam Mulai")

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(selectedTime, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Durasi")

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedButton(
            onClick = { showDurationDialog = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text("$selectedDuration Jam", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total harga:", fontSize = 16.sp)
            Text("150.000", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
            Text("Kirimkan bukti pembayaran", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { showConfirmationDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D65A8))
        ) {
            Text("Booking", fontSize = 16.sp, color = Color.White)
        }
    }
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val date = Date(it)
                            selectedDate =
                                SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
                        }
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) { Text("Batal") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        CustomTimePicker(
            onTimeSelected = { time ->
                selectedTime = time
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
    if (showDurationDialog) {
        AlertDialog(
            onDismissRequest = { showDurationDialog = false },
            title = { Text("Pilih Durasi Booking") },
            text = {
                Column {
                    (1..6).forEach { hours ->
                        RadioButtonItem(
                            hours = hours,
                            selected = selectedDuration == hours,
                            onSelect = { selectedDuration = hours }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showDurationDialog = false }
                ) {
                    Text("OK")
                }
            }
        )
    }
    if (showConfirmationDialog) {
        BookingConfirmationDialog(
            bookingCode = "SLP1451", // Replace with actual booking code
            onDismiss = { showConfirmationDialog = false }
        )
    }
    if(showBuktiPembayaran) {
        UploadPhotoScreen()
    }
}

@Composable
fun RadioButtonItem(hours: Int, selected: Boolean, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .selectable(
                selected = selected,
                onClick = onSelect
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = {}
        )
        Text(
            text = if (hours == 1) "1 jam" else "$hours jam",
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CustomTimePicker(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var hour by remember { mutableStateOf(8) }
    var minute = 0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pilih Waktu") },
        text = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { if (hour > 0) hour-- }) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Kurangi jam")
                    }
                    Text("Jam $hour", modifier = Modifier.padding(horizontal = 16.dp))
                    IconButton(onClick = { if (hour < 23) hour++ }) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Tambah jam")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onTimeSelected("${hour}:${minute.toString().padStart(2, '0')}")
                }
            ) {
                Text("Pilih")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}

@Composable
fun BookingConfirmationDialog(
    bookingCode: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = "Pembayaran diterima!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50), // Green color for success
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Text(
                    text = "KODE BOOKING:",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = bookingCode,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tutup", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun UploadPhotoScreen() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
            uri?.let { uploadPhoto(it, context) }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { launcher.launch("image/*") }
        ) {
            Text("Pilih Foto")
        }

        imageUri?.let { uri ->
            ImagePreview(uri)
        }
    }
}

private fun uploadPhoto(uri: Uri, context: Context) {
    Toast.makeText(context, "Mengupload foto...", Toast.LENGTH_SHORT).show()


}

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

@Preview(showBackground = true)
@Composable
fun BookingConfirmationDialogPreview() {
    MaterialTheme {
        BookingConfirmationDialog(
            bookingCode = "SLP1451",
            onDismiss = { /* Handle dismiss */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookingDetailScreenPreview() {
    SilaporTheme {
        BookingDetailContent(
            field = DataFieldDetail(
                id = 1,
                nama = "Sushi Roll",
                foto = "https://jynmzifknyokgtbzddsr.supabase.co/storage/v1/object/public/imageupload/lapangan/1/68124e4eb1f16.png",
                harga = 200000,
                jamBuka = "12:00",
                jamTutup = "16:00",
                kota = "Bandung",
                lokasi = "JL.Bandung",
                linkLokasi = "www.com",
                jadwal = mapOf(
                    "2025-05-02" to listOf(
                        JamTersedia(jam = "12:00", jadwalTersedia = "tersedia"),
                        JamTersedia(jam = "13:00", jadwalTersedia = "tersedia"),
                        JamTersedia(jam = "14:00", jadwalTersedia = "dipesan")
                    )
                )
            ),
            onBackClick = {},
        )
    }
}