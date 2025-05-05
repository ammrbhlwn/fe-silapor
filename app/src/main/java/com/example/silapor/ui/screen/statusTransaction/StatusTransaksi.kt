package com.example.silapor.ui.screen.statusTransaction

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.silapor.di.Injection
import com.example.silapor.ui.ViewModelFactory
import com.example.silapor.ui.common.UiState
import com.example.silapor.ui.components.BookingTransactionCard
import com.example.silapor.ui.components.LoaderAnimation
import com.example.silapor.ui.theme.BluePrimary
import com.example.silapor.ui.theme.SilaporTheme

@Composable
fun StatusTransaksiScreen(
    modifier: Modifier = Modifier,
    viewModel: StatusTransaksiViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    var bookingCode by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val statusState by viewModel.uiState.collectAsState()

    val isFormValid = bookingCode.isNotBlank() &&
            phoneNumber.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "Cek Status Transaksi",
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = bookingCode,
            onValueChange = { bookingCode = it },
            label = { Text("Kode Booking") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("No Telepon") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BluePrimary,
                contentColor = Color.White
            ),
            enabled = isFormValid,
            onClick = {
                isLoading = true
                viewModel.checkTransactionStatus(phoneNumber, bookingCode)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Cari")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (statusState) {
            is UiState.Loading -> {
                if (isLoading) {
                    LoaderAnimation()
                }
            }
            is UiState.Success -> {
                val booking = (statusState as UiState.Success).data
                BookingTransactionCard(booking = booking)
                isLoading = false
            }
            is UiState.Error -> {
                Text(
                    text = (statusState as UiState.Error).error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                isLoading = false
            }
            is UiState.Empty -> {}
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun StatusTransaksiPreview() {
    SilaporTheme {
        StatusTransaksiScreen()
    }
}
