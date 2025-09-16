package com.example.exerciseslot3.ui.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import com.example.exerciseslot3.R
import android.widget.Toast

@Composable
fun MartfuryBottomNavigation() {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        val context = LocalContext.current
        val items = listOf(
            Triple(Icons.Outlined.Home, stringResource(id = R.string.home), true),
            Triple(ImageVector.vectorResource(id = R.drawable.grid_view_24px), stringResource(id = R.string.categories), false),
            Triple(ImageVector.vectorResource(id = R.drawable.explore_24px), stringResource(id = R.string.explorer), false),
            Triple(ImageVector.vectorResource(id = R.drawable.shopping_bag_24px), stringResource(id = R.string.cart), false),
            Triple(Icons.Outlined.Person, stringResource(id = R.string.profile), false)
        )

        items.forEachIndexed { index, (icon, label, selected) ->
            NavigationBarItem(
                icon = {
                    if (index == 3) {
                        BadgedBox(
                            badge = {
                                Badge(containerColor = MaterialTheme.colorScheme.error) {
                                    Text("1")
                                }
                            }
                        ) {
                            Icon(imageVector = icon, contentDescription = label)
                        }
                    } else {
                        Icon(imageVector = icon, contentDescription = label)
                    }
                },
                label = { Text(text = label) },
                selected = selected,
                onClick = {
                    Toast.makeText(context, "$label clicked", Toast.LENGTH_SHORT).show()
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}


