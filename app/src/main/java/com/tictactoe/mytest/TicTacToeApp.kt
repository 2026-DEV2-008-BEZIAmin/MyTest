package com.tictactoe.mytest

import android.app.Application
import com.tictactoe.mytest.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TicTacToeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TicTacToeApp)
            modules(appModule)
        }
    }
}
