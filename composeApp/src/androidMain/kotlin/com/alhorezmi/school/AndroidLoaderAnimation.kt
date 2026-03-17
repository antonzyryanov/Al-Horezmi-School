package com.alhorezmi.school.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.alhorezmi.school.R

@Composable
actual fun LoaderAnimation(modifier: Modifier) {
    AsyncImage(
        model = R.drawable.loader,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}
