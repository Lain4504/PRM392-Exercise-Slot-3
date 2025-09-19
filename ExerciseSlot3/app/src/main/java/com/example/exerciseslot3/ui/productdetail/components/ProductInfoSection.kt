package com.example.exerciseslot3.ui.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun ProductInfoSection() {
    val context = LocalContext.current
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
                Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFC107))
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFC107))
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = Color(0xFFBDBDBD))
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = Color(0xFFBDBDBD))
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = Color(0xFFBDBDBD))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "8", color = Color.Gray, fontSize = 13.sp)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                RoundIconButton { Icon(painter = painterResource(id = R.drawable.sync_alt_24px), contentDescription = null, tint = Color.Gray) }
                RoundIconButton { Icon(imageVector = Icons.Filled.Favorite, contentDescription = null, tint = Color(0xFFE53935)) }
                RoundIconButton { Icon(imageVector = Icons.Filled.Share, contentDescription = null, tint = Color.Gray) }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = colorResource(id = R.color.martfury_light_gray))

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Categories:", color = Color.Black, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(12.dp))
            val links = listOf("Wireless Speakers", "Digital Cables", "Computers", "Monitors")
            FlowingLinks(links) { clicked ->
                Toast.makeText(context, "$clicked clicked", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Text(text = "SKU:", color = Color.Black, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "LI-139", color = colorResource(id = R.color.martfury_secondary_text))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowingLinks(links: List<String>, onClick: (String) -> Unit = {}) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        links.forEachIndexed { i, s ->
            Row(modifier = Modifier.clickable { onClick(s) }) {
                Text(text = s, color = colorResource(id = R.color.martfury_primary))
                if (i != links.lastIndex) Text(text = ",", color = colorResource(id = R.color.martfury_primary))
            }
        }
    }
}

@Composable
private fun RoundIconButton(content: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, colorResource(id = R.color.martfury_light_gray), CircleShape)
    ) {
        content()
    }
}


