package com.example.silapor.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.silapor.ui.theme.Yellow
import com.example.silapor.ui.theme.SilaporTheme

@Composable
fun RatingFavorite (
    fieldId: Int,
    fieldRating: String,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // icon star
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "rating_icon",
            tint = Yellow,
            modifier = modifier
                .size(40.dp)
        )

        Spacer(modifier = modifier.width(4.dp))

        //rating
        Text(
            text = fieldRating,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = modifier.width(8.dp))

        //icon favorite
        Icon(
            imageVector = if (true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "favorite_button",
            tint = Color.Red,
            modifier = modifier
                .size(40.dp)
                .clickable { onFavoriteClick(fieldId) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RatingFavoritePreview() {
    SilaporTheme {
        RatingFavorite (
            fieldId = 1,
            fieldRating = "4.8",
            onFavoriteClick = {}
        )
    }
}