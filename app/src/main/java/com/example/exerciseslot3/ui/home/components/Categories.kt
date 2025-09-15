package com.example.exerciseslot3.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R

data class CategoryItem(val name: String, val imageResId: Int)

@Composable
fun FeaturedCategories() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.featured_categories),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = "1/2",
                color = colorResource(id = R.color.martfury_secondary_text),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val categories = listOf(
            CategoryItem(stringResource(id = R.string.electronics), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.clothing), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.computers), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.home_kitchen), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.health_beauty), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.jewelry_watch), R.drawable.ic_launcher_foreground)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            userScrollEnabled = false,
            modifier = Modifier.height(250.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(2) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == 0) colorResource(id = R.color.martfury_primary)
                            else colorResource(id = R.color.martfury_light_gray)
                        )
                        .padding(horizontal = 2.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Composable
fun CategoryCard(category: CategoryItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.martfury_gray))
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = category.imageResId),
            contentDescription = category.name,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.name,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}


