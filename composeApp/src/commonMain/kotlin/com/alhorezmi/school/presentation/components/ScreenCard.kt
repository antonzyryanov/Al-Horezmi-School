package com.alhorezmi.school.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.CardSand

@Composable
fun ScreenCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimens.cardCorner),
        colors = CardDefaults.cardColors(containerColor = CardSand.copy(alpha = 0.94f)),
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(AppDimens.contentSpacing),
            content = content,
        )
    }
}
