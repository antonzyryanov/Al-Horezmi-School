package com.alhorezmi.school.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.InkBlack
import com.alhorezmi.school.app_design.SandYellow
import com.alhorezmi.school.app_design.WarmPaper

@Composable
fun AnswerOptionRow(
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(2.dp, InkBlack.copy(alpha = 0.1f), RoundedCornerShape(18.dp))
            .background(WarmPaper)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppDimens.smallSpacing),
    ) {
        Checkbox(
            checked = false,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = SandYellow,
                uncheckedColor = InkBlack,
                checkmarkColor = InkBlack,
            ),
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = InkBlack,
            modifier = Modifier.weight(1f),
        )
    }
}
