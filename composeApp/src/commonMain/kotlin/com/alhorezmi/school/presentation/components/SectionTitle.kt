package com.alhorezmi.school.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.alhorezmi.school.app_design.WarmPaper

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        color = WarmPaper,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}
