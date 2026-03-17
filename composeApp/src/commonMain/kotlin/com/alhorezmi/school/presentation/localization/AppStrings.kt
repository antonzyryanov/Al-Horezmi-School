package com.alhorezmi.school.presentation.localization

import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.QuizTheme

data class AppStrings(
    val appTitle: String,
    val enterNameTitle: String,
    val enterNamePlaceholder: String,
    val continueText: String,
    val records: String,
    val settings: String,
    val score: String,
    val music: String,
    val language: String,
    val resetAll: String,
    val resetTheme: String,
    val backToMenu: String,
    val yourScore: String,
    val quoteLow: String,
    val quoteMedium: String,
    val quoteHigh: String,
    val questionProgress: String,
    val noRecords: String,
    val chooseTheme: String,
    val loading: String,
)

object LocalizationRepository {
    fun strings(language: AppLanguage): AppStrings = when (language) {
        AppLanguage.ENGLISH -> AppStrings(
            appTitle = "Al Horezmi School",
            enterNameTitle = "Enter your name, student",
            enterNamePlaceholder = "Your name",
            continueText = "Continue",
            records = "Records",
            settings = "Settings",
            score = "Score",
            music = "Background Music",
            language = "Language",
            resetAll = "Reset all progress",
            resetTheme = "Reset theme progress",
            backToMenu = "Back to menu",
            yourScore = "Final score",
            quoteLow = "I think You should try again, my student",
            quoteMedium = "You have shown a lot of wisdom, my student. But you can do better.",
            quoteHigh = "This is very impressive. You will be able to join the ranks of Baghdad's scientists in time",
            questionProgress = "Question",
            noRecords = "No records yet.",
            chooseTheme = "Choose a theme",
            loading = "Loading...",
        )
        AppLanguage.ARABIC -> AppStrings(
            appTitle = "مدرسة الخوارزمي",
            enterNameTitle = "أدخل اسمك يا تلميذي",
            enterNamePlaceholder = "اسمك",
            continueText = "متابعة",
            records = "السجلات",
            settings = "الإعدادات",
            score = "النتيجة",
            music = "موسيقى الخلفية",
            language = "اللغة",
            resetAll = "إعادة تعيين كل التقدم",
            resetTheme = "إعادة تعيين تقدم القسم",
            backToMenu = "العودة إلى القائمة",
            yourScore = "النتيجة النهائية",
            quoteLow = "أظن أنك يجب أن تحاول مرة أخرى يا تلميذي",
            quoteMedium = "لقد أظهرت كثيراً من الحكمة يا تلميذي، لكن يمكنك أن تفعل أفضل من ذلك.",
            quoteHigh = "هذا مدهش جداً. ستتمكن مع الوقت من الانضمام إلى علماء بغداد",
            questionProgress = "السؤال",
            noRecords = "لا توجد سجلات بعد.",
            chooseTheme = "اختر موضوعاً",
            loading = "جاري التحميل...",
        )
        AppLanguage.RUSSIAN -> AppStrings(
            appTitle = "Школа Аль-Хорезми",
            enterNameTitle = "Введите ваше имя, ученик",
            enterNamePlaceholder = "Ваше имя",
            continueText = "Продолжить",
            records = "Рекорды",
            settings = "Настройки",
            score = "Счёт",
            music = "Фоновая музыка",
            language = "Язык",
            resetAll = "Сбросить весь прогресс",
            resetTheme = "Сбросить прогресс темы",
            backToMenu = "Меню",
            yourScore = "Итоговый счёт",
            quoteLow = "Думаю, тебе стоит попробовать снова, мой ученик",
            quoteMedium = "Ты показал много мудрости, мой ученик. Но ты можешь лучше.",
            quoteHigh = "Это очень впечатляет. Со временем ты сможешь присоединиться к рядам учёных Багдада",
            questionProgress = "Вопрос",
            noRecords = "Пока нет рекордов.",
            chooseTheme = "Выберите тему",
            loading = "Загрузка...",
        )
    }

    fun themeLabel(language: AppLanguage, theme: QuizTheme): String = when (language) {
        AppLanguage.ENGLISH -> when (theme) {
            QuizTheme.MATH -> "Math"
            QuizTheme.PHYSICS -> "Physics"
            QuizTheme.CHEMISTRY -> "Chemistry"
            QuizTheme.JURISPRUDENCE -> "Jurisprudence"
            QuizTheme.ASTRONOMY -> "Astronomy"
        }
        AppLanguage.ARABIC -> when (theme) {
            QuizTheme.MATH -> "الرياضيات"
            QuizTheme.PHYSICS -> "الفيزياء"
            QuizTheme.CHEMISTRY -> "الكيمياء"
            QuizTheme.JURISPRUDENCE -> "الفقه"
            QuizTheme.ASTRONOMY -> "الفلك"
        }
        AppLanguage.RUSSIAN -> when (theme) {
            QuizTheme.MATH -> "Математика"
            QuizTheme.PHYSICS -> "Физика"
            QuizTheme.CHEMISTRY -> "Химия"
            QuizTheme.JURISPRUDENCE -> "Правоведение"
            QuizTheme.ASTRONOMY -> "Астрономия"
        }
    }
}
