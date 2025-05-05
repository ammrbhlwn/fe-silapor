package com.example.silapor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.silapor.data.remote.response.DataBookingDetail
import com.example.silapor.ui.theme.SilaporTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BookingTransactionCard(booking: DataBookingDetail) {

    val statusColor = when (booking.statusTransaksi.lowercase()) {
        "menunggu" -> Color(0xFFFFF59D)
        "disetujui" -> Color(0xFFA5D6A7)
        "ditolak" -> Color(0xFFEF9A9A)
        "bermain" -> Color(0xFF90CAF9)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val formattedHarga = NumberFormat
        .getNumberInstance(Locale("id", "ID"))
        .format(booking.totalHarga)

    val jamMulaiFormatted = booking.jamMulai.substring(0, 5)
    val jamSelesaiFormatted = booking.jamSelesai.substring(0, 5)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            BookingCardRow(label = "Tanggal Booking", value = booking.tanggalBooking)
            BookingCardRow(label = "Nama Lapangan", value = booking.namaLapangan)
            BookingCardRow(label = "Lokasi Lapangan", value = booking.lokasi)
            BookingCardRow(label = "Jam Booking", value = "$jamMulaiFormatted - $jamSelesaiFormatted")
            BookingCardRow(label = "Total Bayar", value = "Rp $formattedHarga")
            BookingCardRow(label = "Penyewa", value = booking.namaPenyewa)
            BookingCardRow(label = "No HP", value = booking.nomorHp)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status Transaksi",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = booking.statusTransaksi.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .background(
                            color = statusColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Bukti Pembayaran",
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(12.dp))

            AsyncImage(
                model = booking.buktiPembayaran,
                contentDescription = "Bukti Pembayaran",
                modifier = Modifier
                    .height(250.dp)
                    .align(Alignment.Start),
                contentScale = ContentScale.FillHeight
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookingTransactionCardPreview() {
    val dummyBooking = DataBookingDetail(
        jamMulai = "10:00",
        foto = "",
        lokasi = "Gor ABC",
        jamSelesai = "12:00",
        totalHarga = 100000,
        id = 1,
        tanggalBooking = "2025-05-05",
        buktiPembayaran = "",
        statusTransaksi = "bermain",
        namaPenyewa = "Budi",
        nomorHp = "081234567890",
        namaLapangan = "Lapangan A"
    )

    SilaporTheme {
        BookingTransactionCard(booking = dummyBooking)
    }
}
