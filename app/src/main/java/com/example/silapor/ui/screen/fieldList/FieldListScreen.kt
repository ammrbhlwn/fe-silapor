package com.example.silapor.ui.screen.fieldList

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.silapor.data.remote.response.DataField
import com.example.silapor.di.Injection
import com.example.silapor.ui.ViewModelFactory
import com.example.silapor.ui.common.UiState
import com.example.silapor.ui.components.FieldItem

@Composable
fun FieldListScreen(
    sportType: String,
    modifier: Modifier = Modifier,
    viewModel: FieldListViewModel = viewModel(
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
                FieldListContent(
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
fun FieldListContent(
    fields : List<DataField>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val columnCount = if (isLandscape) 2 else 1

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(12.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
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
