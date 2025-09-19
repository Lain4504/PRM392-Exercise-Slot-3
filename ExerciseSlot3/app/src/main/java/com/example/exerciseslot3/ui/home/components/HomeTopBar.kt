package com.example.exerciseslot3.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot3.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import android.widget.Toast

@Composable
fun HomeTopBar() {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        val context = LocalContext.current
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 60.dp, bottom = 2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "mart",
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "fury",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorites",
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "Favorites clicked", Toast.LENGTH_SHORT).show()
                        }
                    )

                    BadgedBox(
                        badge = {
                            Badge(containerColor = Color.Black) {
                                Text("2", color = Color.White, fontSize = 10.sp)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sync_alt_24px),
                            contentDescription = "Compare",
                            tint = Color.Black,
                            modifier = Modifier.clickable {
                                Toast.makeText(context, "Compare clicked", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }

            SearchBar()
        }
    }
}


