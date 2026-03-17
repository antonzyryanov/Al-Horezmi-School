package com.alhorezmi.school.domain.model

data class AppSettings(
    val musicEnabled: Boolean = true,
    val language: AppLanguage = AppLanguage.ENGLISH,
)
