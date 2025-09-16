package com.example.exerciseslot3.ui.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.exerciseslot3.R
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

@Composable
fun ProductDetailBottomBar() {
    val context = LocalContext.current
    Surface(shadowElevation = 8.dp) {
        Column(modifier = Modifier.fillMaxWidth()) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 36.dp)
            ) {
                OutlinedButton(
                    onClick = { Toast.makeText(context, "Add to Cart clicked", Toast.LENGTH_SHORT).show() },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add to Cart", color = Color.Black)
                }

                Button(
                    onClick = { Toast.makeText(context, "Buy Now clicked", Toast.LENGTH_SHORT).show() },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.martfury_primary)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Buy Now", color = Color.White)
                }
            }
        }
    }
}


