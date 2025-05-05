package com.example.silapor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.silapor.R
import com.example.silapor.model.DummyFieldData
import com.example.silapor.ui.theme.SilaporTheme

@Composable
fun SportGrid(
    modifier: Modifier,
    selectedSport: String?,
    onSportSelected: (String) -> Unit
) {
    val sports = DummyFieldData.sports

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
    ) {
        items(sports) { sport ->
            SportGridItem(
                imageUrl = sport.imageUrl,
                name = sport.name,
                isSelected = selectedSport == sport.name,
                onClick = { onSportSelected(sport.name) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SportGridPreview() {
    SilaporTheme {
        SportGrid(
            selectedSport = stringResource(R.string.futsal),
            onSportSelected = {},
            modifier = Modifier
        )
    }
}