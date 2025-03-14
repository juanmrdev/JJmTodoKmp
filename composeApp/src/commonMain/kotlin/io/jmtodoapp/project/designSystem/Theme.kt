package io.jmtodoapp.project.designSystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        primaryBlue = PrimaryBlue,
        secondaryGreen = SecondaryGreen,
        accentOrange = AccentOrange
    )
}

data class AppColors(
    val primaryBlue: Color,
    val secondaryGreen: Color,
    val accentOrange: Color
)

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val typography = Typography(
        headlineMedium = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = colorScheme.onBackground
        ),
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = colorScheme.onBackground
        ),
        bodySmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = colorScheme.onSurface
        )
    )

    CompositionLocalProvider(LocalAppColors provides AppColors(PrimaryBlue, SecondaryGreen, AccentOrange)) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}