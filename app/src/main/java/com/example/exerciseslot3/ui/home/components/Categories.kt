package com.example.exerciseslot3.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R
import android.widget.Toast

data class CategoryItem(val name: String, val imageResId: Int)

@Composable
fun FeaturedCategories() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        val context = LocalContext.current
        // Prepare data and paging state first so header can show current page
        val baseCategories = listOf(
            CategoryItem(stringResource(id = R.string.electronics), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.clothing), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.computers), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.home_kitchen), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.health_beauty), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.jewelry_watch), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.sports), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.toys), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.books), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.automotive), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.garden), R.drawable.ic_launcher_foreground),
            CategoryItem(stringResource(id = R.string.pets), R.drawable.ic_launcher_foreground)
        )
        val categories = remember { baseCategories }
        val pages: List<List<CategoryItem>> = remember(categories) { categories.chunked(6) }
        val listState = rememberLazyListState()
        var currentPage by remember { mutableStateOf(0) }

        LaunchedEffect(listState.firstVisibleItemIndex) {
            currentPage = listState.firstVisibleItemIndex
        }
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
                text = "${currentPage + 1}/${pages.size}",
                color = colorResource(id = R.color.martfury_secondary_text),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        // Horizontal pager of category pages
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(pages.size) { pageIndex ->
                Column(modifier = Modifier
                    .fillParentMaxWidth()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        userScrollEnabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp) // ensure full cards visible
                    ) {
                        items(pages[pageIndex]) { category ->
                            CategoryCard(
                                category = category,
                                onClick = {
                                    Toast.makeText(context, "${category.name} clicked", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Dots indicator moved lower, reflects current page
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(pages.size) { index ->
                if (index == currentPage) {
                    Box(
                        modifier = Modifier
                            .width(18.dp)
                            .height(8.dp)
                            .clip(RoundedCornerShape(50))
                            .background(colorResource(id = R.color.martfury_primary))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(colorResource(id = R.color.martfury_light_gray))
                    )
                }
                if (index != pages.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: CategoryItem, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.martfury_gray))
            .clickable { onClick() }
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


