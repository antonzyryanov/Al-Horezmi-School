package com.alhorezmi.school.domain.model

enum class QuizTheme(val fileName: String) {
    MATH("math"),
    PHYSICS("physics"),
    CHEMISTRY("chemistry"),
    JURISPRUDENCE("jurisprudence"),
    ASTRONOMY("astronomy");

    companion object {
        fun fromThemeName(themeName: String): QuizTheme =
            entries.firstOrNull { it.name.equals(themeName, ignoreCase = true) || it.fileName.equals(themeName, ignoreCase = true) }
                ?: MATH
    }
}
