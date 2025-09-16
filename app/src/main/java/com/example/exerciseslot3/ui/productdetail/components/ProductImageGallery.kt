package com.example.exerciseslot3.ui.productdetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R

@Composable
fun ProductImageGallery() {
    val images = listOf(
        R.drawable.iphone_1,
        R.drawable.iphone_2,
        R.drawable.iphone_3,
        R.drawable.iphone_4
    )

    val listState = rememberLazyListState()
    var currentIndex by remember { mutableStateOf(0) }
    LaunchedEffect(listState.firstVisibleItemIndex) {
        currentIndex = listState.firstVisibleItemIndex
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        LazyRow(state = listState) {
            items(images.size) { index ->
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = "Product Image ${index + 1}",
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(480.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { Toast.makeText(context, "Image ${index + 1} clicked", Toast.LENGTH_SHORT).show() },
                    contentScale = ContentScale.Fit
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0x88000000))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = "${currentIndex + 1}/${images.size}", color = Color.White, fontSize = 12.sp)
        }
    }
}


