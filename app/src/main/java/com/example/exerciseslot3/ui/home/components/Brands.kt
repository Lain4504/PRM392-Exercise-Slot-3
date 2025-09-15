package com.example.exerciseslot3.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R

@Composable
fun FeaturedBrands() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)) {
        Text(
            text = stringResource(id = R.string.featured_brands),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        val brands = listOf(
            "Hostlare", "Patel Pinch", "Panax Pharma"
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(brands.size) { index ->
                BrandCard(brands[index])
            }
        }
    }
}

@Composable
fun BrandCard(brandName: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .size(120.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.martfury_light_gray))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = brandName,
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}


