package com.alhorezmi.school.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.app_icon
import org.jetbrains.compose.resources.painterResource

@Composable
actual fun LoaderAnimation(modifier: Modifier) {
    Image(
        painter = painterResource(Res.drawable.app_icon),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.size(200.dp),
    )
}
