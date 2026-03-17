package com.alhorezmi.school.domain.model

enum class AppLanguage(val tag: String) {
    ENGLISH("en"),
    ARABIC("ar"),
    RUSSIAN("ru");

    companion object {
        fun fromTag(tag: String): AppLanguage = entries.firstOrNull { it.tag == tag } ?: ENGLISH
    }
}
