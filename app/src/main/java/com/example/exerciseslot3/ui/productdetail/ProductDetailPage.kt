package com.example.exerciseslot3.ui.productdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.exerciseslot3.ui.productdetail.components.ProductDetailBottomBar
import com.example.exerciseslot3.ui.productdetail.components.ProductDetailTopBar
import com.example.exerciseslot3.ui.productdetail.components.ProductImageGallery
import com.example.exerciseslot3.ui.productdetail.components.ProductInfoSection
import com.example.exerciseslot3.ui.theme.ExerciseSlot3Theme

@Composable
fun ProductDetailPage() {
    Scaffold(topBar = { ProductDetailTopBar() }, bottomBar = { ProductDetailBottomBar() }) { inner ->
        Column(modifier = Modifier.fillMaxSize().padding(inner)) {
            ProductImageGallery()
            ProductInfoSection()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailPreview() {
    ExerciseSlot3Theme { ProductDetailPage() }
}


