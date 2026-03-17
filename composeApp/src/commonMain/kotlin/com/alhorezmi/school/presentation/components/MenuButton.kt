package com.alhorezmi.school.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alhorezmi.school.app_design.InkBlack

@Composable
fun MenuButton(
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit,
) {
    ScreenCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge, color = InkBlack)
        subtitle?.takeIf { it.isNotBlank() }?.let {
            Text(text = it, style = MaterialTheme.typography.bodyMedium, color = InkBlack)
        }
    }
}
