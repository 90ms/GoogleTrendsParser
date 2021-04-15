package com.a90ms.nowrtf

import android.app.Application
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import com.a90ms.nowrtf.util.Constants.PREF_THEME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RSSApp : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTheme()
    }

    private fun setupTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val mode = preferences.getBoolean(PREF_THEME, false)
        if (mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        }
    }
}