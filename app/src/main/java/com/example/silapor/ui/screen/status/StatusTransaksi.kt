import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatusTransaksiScreen() {
    var bookingCode by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<BookingHistory>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "History",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

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
            onClick = {
                searchResults = searchBookingHistory(bookingCode, phoneNumber)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cari")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (searchResults.isNotEmpty()) {
            LazyColumn {
                items(searchResults) { booking ->
                    BookingHistoryCard(booking = booking)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else if (bookingCode.isNotEmpty() || phoneNumber.isNotEmpty()) {
            Text(
                text = "Tidak ada hasil ditemukan",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar()
    }
}

@Composable
fun BookingHistoryCard(booking: BookingHistory) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = booking.date,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = booking.venue,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = booking.activity,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = booking.price,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(onClick = { /* Navigasi ke Home */ }) {
            Text("Home")
        }
        TextButton(onClick = { /* Navigasi ke History */ }) {
            Text("History")
        }
    }
}

private fun searchBookingHistory(bookingCode: String, phoneNumber: String): List<BookingHistory> {
    return if (bookingCode.isNotBlank() && phoneNumber.isNotBlank()) {
        listOf(
            BookingHistory(
                date = "02/05/2025",
                venue = "Elite Futsal",
                activity = "Disetujui",
                price = "Rp.150.000"
            )
        )
    } else {
        emptyList()
    }
}

data class BookingHistory(
    val date: String,
    val venue: String,
    val activity: String,
    val price: String
)

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    MaterialTheme {
        StatusTransaksiScreen()
    }
}
