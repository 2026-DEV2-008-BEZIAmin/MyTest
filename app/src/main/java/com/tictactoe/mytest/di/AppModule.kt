package com.tictactoe.mytest.di

import com.tictactoe.mytest.domain.rules.WinChecker
import com.tictactoe.mytest.domain.usecase.PlayMoveUseCase
import com.tictactoe.mytest.ui.TicTacToeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { WinChecker() }
    single { PlayMoveUseCase(get()) }
    viewModel { TicTacToeViewModel(get()) }
}
