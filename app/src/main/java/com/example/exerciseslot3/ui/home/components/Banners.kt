package com.example.exerciseslot3.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.exerciseslot3.R
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

@Composable
fun PromotionalBanners() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val context = LocalContext.current
        BannerCard(
            imageResId = R.drawable.banner_01,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clickable { Toast.makeText(context, "Banner 1 clicked", Toast.LENGTH_SHORT).show() }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BannerCard(
                imageResId = R.drawable.banner_02,
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .clickable { Toast.makeText(context, "Banner 2 clicked", Toast.LENGTH_SHORT).show() }
            )

            BannerCard(
                imageResId = R.drawable.banner_03,
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .clickable { Toast.makeText(context, "Banner 3 clicked", Toast.LENGTH_SHORT).show() }
            )
        }
    }
}

@Composable
fun BannerCard(
    imageResId: Int,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(120.dp)
            .then(modifier)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        )
    }
}


