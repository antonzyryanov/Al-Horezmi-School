package com.alhorezmi.school.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.InkBlack
import com.alhorezmi.school.app_design.SandYellow

@Composable
fun ScoreBadge(scoreLabel: String, scoreValue: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimens.scoreCorner),
        color = SandYellow.copy(alpha = 0.92f),
        tonalElevation = 6.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "$scoreLabel: $scoreValue",
                style = MaterialTheme.typography.labelLarge,
                color = InkBlack,
            )
        }
    }
}
