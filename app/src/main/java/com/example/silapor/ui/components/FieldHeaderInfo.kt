package com.example.silapor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.silapor.data.remote.response.DataFieldDetail
import com.example.silapor.data.remote.response.JamTersedia
import com.example.silapor.ui.theme.SilaporTheme

@Composable
fun FieldHeaderInfo(
    field: DataFieldDetail,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        //field name
        Text(
            text = field.nama,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FieldTag(
                fieldPrice = field.harga,
            )

            RatingFavorite(
                fieldId = field.id,
                fieldRating = "2",
                onFavoriteClick = onFavoriteClick
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FieldHeaderInfoPreview() {
    SilaporTheme {
        FieldHeaderInfo(
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
            onFavoriteClick = {}
        )
    }
}