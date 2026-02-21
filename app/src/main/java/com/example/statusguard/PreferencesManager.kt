package com.example.statusguard

import android.content.Context
import java.time.LocalTime

object PreferencesManager {
    private const val PREF_NAME = "status_guard_prefs"
    private const val KEY_START_HOUR = "start_hour"
    private const val KEY_START_MINUTE = "start_minute"
    private const val KEY_END_HOUR = "end_hour"
    private const val KEY_END_MINUTE = "end_minute"

    fun saveTimes(context: Context, start: LocalTime, end: LocalTime) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_START_HOUR, start.hour)
            .putInt(KEY_START_MINUTE, start.minute)
            .putInt(KEY_END_HOUR, end.hour)
            .putInt(KEY_END_MINUTE, end.minute)
            .apply()
    }

    fun getStartTime(context: Context): LocalTime {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return LocalTime.of(
            prefs.getInt(KEY_START_HOUR, 20),
            prefs.getInt(KEY_START_MINUTE, 0)
        )
    }

    fun getEndTime(context: Context): LocalTime {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return LocalTime.of(
            prefs.getInt(KEY_END_HOUR, 21),
            prefs.getInt(KEY_END_MINUTE, 0)
        )
    }
}
