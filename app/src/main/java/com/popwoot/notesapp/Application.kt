package com.popwoot.notesapp

import android.app.Application
import android.content.Context


class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        @get:Synchronized
        var appContext: Context? = null
            private set

    }
}
