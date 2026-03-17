package com.alhorezmi.school

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidActivityHolder.currentActivity = this
        // Appodeal is initialized lazily on the first ad request so that
        // any SDK crash never prevents the app from launching.
        setContent {
            App()
        }
    }

    override fun onDestroy() {
        if (AndroidActivityHolder.currentActivity === this) {
            AndroidActivityHolder.currentActivity = null
        }
        super.onDestroy()
    }
}
