package com.emredogan.tavlazari

import android.content.Context

class SharedPrefs(context: Context) {
    val PREFERENCE_NAME = "SharedPreferenceExample"
    val PREFERENCE_INTRO_DONT_SHOW = "INTRO_DONT_SHOW"
    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    var dontShowIntro: Boolean
        get() = preference.getBoolean(PREFERENCE_INTRO_DONT_SHOW,false)
        set(value) = preference.edit().putBoolean(PREFERENCE_INTRO_DONT_SHOW, value).apply()

}