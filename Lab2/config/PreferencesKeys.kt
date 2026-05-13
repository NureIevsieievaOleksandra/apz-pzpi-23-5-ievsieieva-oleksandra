package ua.nure.smartlight.config

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val token = stringPreferencesKey("token")
    val appTheme = stringPreferencesKey("appTheme")
}