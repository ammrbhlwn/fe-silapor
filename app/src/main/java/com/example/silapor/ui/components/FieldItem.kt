package com.example.silapor.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.silapor.R
import com.example.silapor.ui.theme.SilaporTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun FieldItem(
    fieldImage: String,
    fieldName: String,
    fieldPrice: Int,
    fieldTimeOpen: String,
    fieldTimeClosed: String,
    fieldCity: String,
    modifier: Modifier = Modifier,
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ){
        Column (
            modifier = modifier
                .padding(16.dp)
        ) {
            AsyncImage(
                model = fieldImage,
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = modifier.height(16.dp))

            Text(
                text = fieldName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = modifier.height(8.dp))

            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = stringResource(R.string.menu_favorite),
                        tint = Color.Black,
                        modifier = modifier
                            .size(24.dp)
                    )

                    Text(
                        text = fieldCity,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        modifier = modifier.weight(1f, fill = false)
                    )
                }

                Spacer(modifier = modifier.height(4.dp))

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.schedule),
                        contentDescription = stringResource(R.string.menu_favorite),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = modifier.width(4.dp))

                    Text(
                        text = "$fieldTimeOpen - $fieldTimeClosed",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            Spacer(modifier = modifier.height(12.dp))

            Text(
                text = "Rp${fieldPrice.formatToThousands()}",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

private fun Int.formatToThousands(): String {
    return NumberFormat.getNumberInstance(Locale.US).format(this)
}

// Preview
@Preview(showBackground = true)
@Composable
fun FieldTimeSlotCardPreview() {
    SilaporTheme {
        FieldItem(
            fieldImage = R.drawable.ic_launcher_background.toString(),
            fieldName = "Inspire Arena Inspire Arena Inspire Arena Inspire Arena Inspire Arena",
            fieldPrice = 75000,
            fieldTimeOpen = "12:00",
            fieldTimeClosed = "16:00",
            fieldCity = "Bandung"
        )
    }
}