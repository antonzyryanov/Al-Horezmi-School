package com.alhorezmi.school.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.DisabledGray
import com.alhorezmi.school.app_design.InkBlack
import com.alhorezmi.school.app_design.SandYellow
import com.alhorezmi.school.app_design.WarmPaper

@Composable
fun PrimaryActionButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(AppDimens.buttonHeight),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) SandYellow else DisabledGray,
            contentColor = InkBlack,
            disabledContainerColor = DisabledGray,
            disabledContentColor = WarmPaper,
        ),
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}
