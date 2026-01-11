package com.tictactoe.mytest.ui

import androidx.lifecycle.ViewModel
import com.tictactoe.mytest.domain.model.Position
import com.tictactoe.mytest.domain.usecase.GameState
import com.tictactoe.mytest.domain.usecase.PlayMoveUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TicTacToeViewModel(
    private val playMoveUseCase: PlayMoveUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(playMoveUseCase.start())
    val state: StateFlow<GameState> = _state

    fun onCellClicked(position: Position) {
        runCatching {
            playMoveUseCase.play(_state.value, position)
        }.onSuccess {
            _state.value = it
        }
    }

    fun restart() {
        _state.value = playMoveUseCase.start()
    }
}
