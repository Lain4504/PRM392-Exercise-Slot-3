package com.example.exerciseslot3.ui.productdetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.clickable

@Composable
fun ProductDetailTopBar() {
    val context = LocalContext.current
    Surface(color = MaterialTheme.colorScheme.primary) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 60.dp, bottom = 8.dp)
        ) {
            // Center title (similar spacing as HomeTopBar)
            Text(
                text = "Back to Shop",
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            // Back icon on the left
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)
                    .clickable { Toast.makeText(context, "Back clicked", Toast.LENGTH_SHORT).show() }
            )

            // Cart with badge on the right
            BadgedBox(
                badge = {
                    Badge(containerColor = Color.Black) {
                        Text("3", color = Color.White, fontSize = 10.sp)
                    }
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.Black,
                    modifier = Modifier.clickable { Toast.makeText(context, "Cart clicked", Toast.LENGTH_SHORT).show() }
                )
            }
        }
    }
}


