package com.alhorezmi.school

import android.app.Application
import com.alhorezmi.school.di.initKoinIfNeeded

class QuizSchoolApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidContextHolder.initialize(this)
        initKoinIfNeeded()
    }
}
