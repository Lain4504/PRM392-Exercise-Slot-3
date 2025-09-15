package com.example.exerciseslot3.ui.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R

@Composable
fun ProductInfoSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Headphone Ultra Bass",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Brand:", color = Color.Gray)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Automotive", color = Color(0xFF1E88E5))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(4) { index ->
                    val starColor = if (index < 3) Color(0xFFFFC107) else Color(0xFFBDBDBD)
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(starColor)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(text = "8", color = Color.Gray, fontSize = 13.sp)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                RoundIconButton(icon = Icons.Filled.ShoppingCart)
                RoundIconButton(icon = Icons.Filled.FavoriteBorder)
                RoundIconButton(icon = Icons.Filled.Share)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = colorResource(id = R.color.martfury_light_gray))

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Categories:", color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            val links = listOf("Wireless Speakers", "Digital Cables", "Computers", "Monitors")
            Column { FlowingLinks(links) }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Text(text = "SKU:", color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "LI-139")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun FlowingLinks(links: List<String>) {
    Row { links.forEachIndexed { i, s ->
        Text(text = s, color = colorResource(id = R.color.martfury_accent))
        if (i != links.lastIndex) Text(text = ",  ")
    } }
}

@Composable
private fun RoundIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, colorResource(id = R.color.martfury_light_gray), CircleShape)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.Gray)
    }
}


