package de.writer_chris.datepicker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = GrayGreen,
    primaryVariant = LightGray,
    secondary = WarmOrange,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = AiryWhite,
    onSecondary = AiryWhite,
    onBackground = AiryWhite,
    onSurface = AiryWhite
)

private val LightColorPalette = lightColors(
    primary = GrayGreen,
    primaryVariant = ExtraLightGray,
    secondary = WarmOrange,
    secondaryVariant = Beige500,
    background = AiryWhite,
    surface = AiryWhite,
    onPrimary = AiryWhite,
    onSecondary = AiryWhite,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun DatePickerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}