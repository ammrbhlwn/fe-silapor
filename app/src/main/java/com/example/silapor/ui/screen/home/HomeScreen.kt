package com.example.silapor.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.silapor.R
import com.example.silapor.di.Injection
import com.example.silapor.ui.ViewModelFactory
import com.example.silapor.ui.components.SportGrid
import com.example.silapor.ui.theme.BluePrimary

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToFieldList: (String) -> Unit,
) {
    val selectedSport by viewModel.selectedSport.collectAsState()

    HomeContent(
        selectedSport = selectedSport,
        onSportSelected = { sport -> viewModel.setSelectedSport(sport) },
        onSearchClicked = {
            selectedSport?.let { sport ->
                navigateToFieldList(sport.lowercase())
            }
        }
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    selectedSport: String?,
    onSportSelected: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(stringResource(
            R.string.choose_sport),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.padding(16.dp))

        SportGrid(
            selectedSport = selectedSport,
            onSportSelected = onSportSelected,
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = onSearchClicked,
            enabled = selectedSport != null,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BluePrimary,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.search_field))
        }
    }
}