package com.example.silapor.ui.screen.fieldDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.silapor.ui.components.DetailImage
import com.example.silapor.ui.components.FieldHeaderInfo
import com.example.silapor.ui.components.OrderButton
import com.example.silapor.ui.components.FieldInfo
import com.example.silapor.data.remote.response.DataFieldDetail
import com.example.silapor.data.remote.response.JamTersedia
import com.example.silapor.di.Injection
import com.example.silapor.ui.ViewModelFactory
import com.example.silapor.ui.common.UiState
import com.example.silapor.ui.theme.SilaporTheme

@Composable
fun FieldDetailScreen(
    fieldId: Int,
    viewModel: FieldDetailViewModel = viewModel(
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
                DetailContent(
                    field = uiState.data,
                    onBackClick = navigateBack,
                    onFavoriteClick = { }
                )
            }
            is UiState.Error -> {
                Text(text = uiState.error)
            }
        }
    }
}

@Composable
fun DetailContent(
    field: DataFieldDetail,
    onBackClick: () -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        //image header
        DetailImage(
            fieldImage = field.foto,
            fieldName = field.nama,
            onBackClick = onBackClick
        )

        Column(
            modifier = modifier.padding(16.dp)
        ) {
            FieldHeaderInfo(
                field = field,
                onFavoriteClick = onFavoriteClick
            )

            Spacer(modifier = modifier.height(24.dp))

            FieldInfo(
                fieldPhoto = field.foto,
                fieldName = field.nama,
                fieldAddress = field.lokasi
            )

            Spacer(modifier = modifier.height(20.dp))

            OrderButton(
                fieldName = field.nama,
                fieldPrice = field.harga,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    SilaporTheme {
        DetailContent(
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
            onFavoriteClick = {},
        )
    }
}
