package com.example.exerciseslot3.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.exerciseslot3.ui.home.components.FeaturedBrands
import com.example.exerciseslot3.ui.home.components.FeaturedCategories
import com.example.exerciseslot3.ui.home.components.HomeTopBar
import com.example.exerciseslot3.ui.home.components.MartfuryBottomNavigation
import com.example.exerciseslot3.ui.home.components.PromotionalBanners

@Composable
fun HomePage() {
    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = { MartfuryBottomNavigation() }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .verticalScroll(rememberScrollState())
        ) {
            PromotionalBanners()
            FeaturedCategories()
            FeaturedBrands()
        }
    }
}


