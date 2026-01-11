package com.tictactoe.mytest.di

import com.tictactoe.mytest.domain.rules.WinChecker
import com.tictactoe.mytest.domain.usecase.PlayMoveUseCase
import org.koin.dsl.module

val appModule = module {

    single { WinChecker() }
    single { PlayMoveUseCase(get()) }
}
