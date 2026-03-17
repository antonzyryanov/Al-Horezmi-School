package com.alhorezmi.school.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.InkBlack
import com.alhorezmi.school.domain.model.PlayerRecord
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.menu_background
import com.alhorezmi.school.presentation.components.BackgroundStage
import com.alhorezmi.school.presentation.components.PrimaryActionButton
import com.alhorezmi.school.presentation.components.ScreenCard
import com.alhorezmi.school.presentation.components.SectionTitle
import com.alhorezmi.school.presentation.localization.AppStrings

@Composable
fun RecordsScreen(
    strings: AppStrings,
    records: List<PlayerRecord>,
    onBack: () -> Unit,
) {
    BackgroundStage(background = Res.drawable.menu_background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(AppDimens.contentSpacing),
        ) {
            SectionTitle(strings.records)
            ScreenCard(modifier = Modifier.fillMaxWidth()) {
                if (records.isEmpty()) {
                    Text(strings.noRecords, color = InkBlack)
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(AppDimens.smallSpacing)) {
                        items(records) { record ->
                            Text(
                                text = "${record.player_name} • ${record.theme_name} • ${record.score}",
                                color = InkBlack,
                            )
                        }
                    }
                }
            }
            PrimaryActionButton(
                text = strings.backToMenu,
                modifier = Modifier.fillMaxWidth(),
                onClick = onBack,
            )
        }
    }
}
