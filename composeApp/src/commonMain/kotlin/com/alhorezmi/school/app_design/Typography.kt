package com.alhorezmi.school.app_design

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.im_fell_english_sc_regular
import org.jetbrains.compose.resources.Font

@Composable
private fun inkQuillFontFamily(): FontFamily =
    FontFamily(
        Font(Res.font.im_fell_english_sc_regular, weight = FontWeight.Normal),
    )

@Composable
fun appTypography(): Typography {
    val quillFont = inkQuillFontFamily()
    return Typography(
        headlineLarge = TextStyle(
            fontFamily = quillFont,
            fontWeight = FontWeight.Normal,
            fontSize = scaledText(34f.sp),
            lineHeight = scaledText(40f.sp),
        ),
        headlineMedium = TextStyle(
            fontFamily = quillFont,
            fontWeight = FontWeight.Normal,
            fontSize = scaledText(28f.sp),
            lineHeight = scaledText(34f.sp),
        ),
        titleLarge = TextStyle(
            fontFamily = quillFont,
            fontWeight = FontWeight.Normal,
            fontSize = scaledText(22f.sp),
            lineHeight = scaledText(28f.sp),
        ),
        bodyLarge = TextStyle(
            fontFamily = quillFont,
            fontWeight = FontWeight.Normal,
            fontSize = scaledText(18f.sp),
            lineHeight = scaledText(25f.sp),
        ),
        bodyMedium = TextStyle(
            fontFamily = quillFont,
            fontWeight = FontWeight.Normal,
            fontSize = scaledText(16f.sp),
            lineHeight = scaledText(22f.sp),
        ),
        labelLarge = TextStyle(
            fontFamily = quillFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = scaledText(16f.sp),
            lineHeight = scaledText(20f.sp),
        ),
    )
}
