package com.emredogan.tavlazari

import android.app.Application

val prefs: SharedPrefs by lazy {
    App.prefs!!
}

class App: Application() {
    companion object {
        var prefs: SharedPrefs? = null
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}