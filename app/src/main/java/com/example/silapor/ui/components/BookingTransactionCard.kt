package com.example.silapor.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.silapor.data.remote.response.DataBookingDetail
import com.example.silapor.ui.theme.SilaporTheme

@Composable
fun BookingTransactionCard(booking: DataBookingDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Tanggal: ${booking.tanggalBooking}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Lokasi: ${booking.lokasi}", style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Nama Lapangan: ${booking.namaLapangan}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary
            )
            Text(
                "Jam: ${booking.jamMulai} - ${booking.jamSelesai}"
            )
            Text(
                "Harga: Rp${booking.totalHarga}"
            )
            Text(
                "Status: ${booking.statusTransaksi}"
            )
            Text(
                "Penyewa: ${booking.namaPenyewa}"
            )
            Text(
                "No HP: ${booking.nomorHp}"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Bukti Pembayaran:", style = MaterialTheme.typography.labelMedium)

            AsyncImage(
                model = booking.buktiPembayaran,
                contentDescription = "Bukti Pembayaran",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookingHistoryCardPreview() {
    val dummyBooking = DataBookingDetail(
        jamMulai = "10:00",
        foto = "",
        lokasi = "Gor ABC",
        jamSelesai = "12:00",
        totalHarga = 100000,
        id = 1,
        tanggalBooking = "2025-05-05",
        buktiPembayaran = "",
        statusTransaksi = "Lunas",
        namaPenyewa = "Budi",
        nomorHp = "081234567890",
        namaLapangan = "Lapangan A"
    )

    SilaporTheme {
        BookingTransactionCard(booking = dummyBooking)
    }
}
