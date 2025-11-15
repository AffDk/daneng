package com.daneng.dict.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Red to Blue gradient colors
private val DeepBlue = Color(0xFF2962FF)
private val MediumBlue = Color(0xFF448AFF)
private val LightBlue = Color(0xFF82B1FF)
private val Pink = Color(0xFFE91E63)
private val LightPink = Color(0xFFF48FB1)
private val DeepRed = Color(0xFFC62828)

private val LightColors = lightColorScheme(
    primary = DeepBlue,
    onPrimary = Color.White,
    primaryContainer = LightBlue,
    onPrimaryContainer = Color(0xFF001A41),
    secondary = Pink,
    onSecondary = Color.White,
    secondaryContainer = LightPink,
    onSecondaryContainer = Color(0xFF3E0019),
    tertiary = MediumBlue,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD1E4FF),
    onTertiaryContainer = Color(0xFF001D35),
    error = DeepRed,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFBFCFF),
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFFBFCFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE1E2EC),
    onSurfaceVariant = Color(0xFF44474F),
    outline = Color(0xFF74777F),
    outlineVariant = Color(0xFFC4C6D0),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF2F3033),
    inverseOnSurface = Color(0xFFF1F0F4),
    inversePrimary = Color(0xFFA8C8FF)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFA8C8FF),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF00497D),
    onPrimaryContainer = Color(0xFFD5E3FF),
    secondary = LightPink,
    onSecondary = Color(0xFF5E1131),
    secondaryContainer = Color(0xFF792847),
    onSecondaryContainer = Color(0xFFFFD9E3),
    tertiary = Color(0xFFBAC9FF),
    onTertiary = Color(0xFF003062),
    tertiaryContainer = Color(0xFF1C478A),
    onTertiaryContainer = Color(0xFFD9E2FF),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF1A1C1E),
    onBackground = Color(0xFFE3E2E6),
    surface = Color(0xFF1A1C1E),
    onSurface = Color(0xFFE3E2E6),
    surfaceVariant = Color(0xFF44474F),
    onSurfaceVariant = Color(0xFFC4C6D0),
    outline = Color(0xFF8E9099),
    outlineVariant = Color(0xFF44474F),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFE3E2E6),
    inverseOnSurface = Color(0xFF1A1C1E),
    inversePrimary = DeepBlue
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(colorScheme = colors, content = content)
}
