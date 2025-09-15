package com.example.exerciseslot3.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R

@Composable
fun HomeTopBar() {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "mart",
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "fury",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorites",
                        tint = Color.Black
                    )

                    BadgedBox(
                        badge = {
                            Badge(containerColor = Color.Black) {
                                Text("2", color = Color.White, fontSize = 10.sp)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Call,
                            contentDescription = "Compare",
                            tint = Color.Black
                        )
                    }
                }
            }

            SearchBar()
        }
    }
}


