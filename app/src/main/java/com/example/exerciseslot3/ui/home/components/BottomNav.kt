package com.example.exerciseslot3.ui.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.exerciseslot3.R

@Composable
fun MartfuryBottomNavigation() {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        val items = listOf(
            Triple(Icons.Outlined.Home, stringResource(id = R.string.home), true),
            Triple(Icons.Outlined.Menu, stringResource(id = R.string.categories), false),
            Triple(Icons.Outlined.Search, stringResource(id = R.string.explorer), false),
            Triple(Icons.Outlined.ShoppingCart, stringResource(id = R.string.cart), false),
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
                onClick = { },
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


