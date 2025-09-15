package com.example.exerciseslot3.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R

@Composable
fun PromotionalBanners() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BannerCard(
                title = stringResource(id = R.string.fabric_discount),
                price = "$279.00",
                backgroundColor = colorResource(id = R.color.martfury_discount_red),
                textColor = Color.White,
                buttonColor = Color.White,
                buttonTextColor = Color.Black,
                imageResId = R.drawable.ic_launcher_foreground
            )
        }

        item {
            BannerCard(
                title = stringResource(id = R.string.iphone_title),
                backgroundColor = Color.White,
                textColor = Color.Black,
                hasDiscount = true,
                discountPercent = "16%",
                imageResId = R.drawable.ic_launcher_foreground
            )
        }

        item {
            BannerCard(
                title = stringResource(id = R.string.leather_bags),
                backgroundColor = Color.White,
                textColor = Color.Black,
                hasDiscount = true,
                discountPercent = "25%",
                imageResId = R.drawable.ic_launcher_foreground
            )
        }
    }
}

@Composable
fun BannerCard(
    title: String,
    price: String? = null,
    backgroundColor: Color,
    textColor: Color,
    buttonColor: Color = colorResource(id = R.color.martfury_primary),
    buttonTextColor: Color = Color.Black,
    hasDiscount: Boolean = false,
    discountPercent: String = "",
    imageResId: Int
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier
            .width(280.dp)
            .height(120.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                if (price != null) {
                    Text(
                        text = price,
                        color = textColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.shop_now),
                            color = buttonTextColor,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (hasDiscount) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(colorResource(id = R.color.martfury_discount_red))
                            .align(Alignment.TopEnd)
                            .padding(top = 8.dp, end = 8.dp)
                    ) {
                        Text(
                            text = discountPercent,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


