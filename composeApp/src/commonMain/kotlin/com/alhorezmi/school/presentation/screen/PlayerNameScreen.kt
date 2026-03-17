package com.alhorezmi.school.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.loader_background
import com.alhorezmi.school.presentation.components.BackgroundStage
import com.alhorezmi.school.presentation.components.PrimaryActionButton
import com.alhorezmi.school.presentation.components.ScreenCard
import com.alhorezmi.school.presentation.components.SectionTitle
import com.alhorezmi.school.presentation.localization.AppStrings

@Composable
fun PlayerNameScreen(
    strings: AppStrings,
    playerName: String,
    onPlayerNameChanged: (String) -> Unit,
    onContinue: () -> Unit,
) {
    BackgroundStage(background = Res.drawable.loader_background) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimens.screenPadding),
            contentAlignment = Alignment.Center,
        ) {
            ScreenCard(modifier = Modifier.fillMaxWidth()) {
                SectionTitle(strings.enterNameTitle)
                OutlinedTextField(
                    value = playerName,
                    onValueChange = onPlayerNameChanged,
                    label = { Text(strings.enterNamePlaceholder) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                PrimaryActionButton(
                    text = strings.continueText,
                    enabled = playerName.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onContinue,
                )
            }
        }
    }
}
