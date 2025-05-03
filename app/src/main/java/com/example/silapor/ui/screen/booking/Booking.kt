package com.example.silapor.ui.screen.booking

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.silapor.R
import com.example.silapor.data.remote.response.DataField
import com.example.silapor.di.Injection
import com.example.silapor.model.Field
import com.example.silapor.ui.ViewModelFactory
import com.example.silapor.ui.common.UiState
import com.example.silapor.ui.components.FieldItem
import com.example.silapor.ui.theme.SilaporTheme


@Composable
fun BookingScreen(
    sportType: String,
    modifier: Modifier = Modifier,
    viewModel: BookingViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    LaunchedEffect(sportType) {
        viewModel.getFieldsByType(sportType)
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Text(text = "Loading")
            }
            is UiState.Success -> {
                BookingContent(
                    modifier = modifier,
                    fields = uiState.data,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {
                Text(text = uiState.error)
            }
        }
    }
}

@Composable
fun BookingContent(
    fields: List<DataField>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val columnCount = if (isLandscape) 2 else 1
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(fields, key = { it.id }) { data ->
                FieldItem(
                    fieldImage = data.foto,
                    fieldName = data.nama,
                    fieldTimeOpen = data.jamBuka,
                    fieldTimeClosed = data.jamTutup,
                    fieldPrice = data.harga,
                    fieldCity = data.kota,
                    modifier = modifier.clickable {
                        navigateToDetail(data.id)
                    }
                )
            }
        }
    }
}

@Composable
fun FieldCard(field: Field) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Lapangan",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(Color(0xFF003366))
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = field.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Rp.${field.pricePerHour}", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview
@Composable
private fun BookingScreenPreview() {
    SilaporTheme {
        BookingScreen(
            "futsal",
            modifier = Modifier,
            navigateToDetail = {},
            viewModel = BookingViewModel(Injection.provideRepository())
        )
    }
}