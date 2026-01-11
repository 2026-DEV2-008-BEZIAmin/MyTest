package com.tictactoe.mytest.domain.usecase

import com.tictactoe.mytest.domain.model.Board
import com.tictactoe.mytest.domain.model.GameResult
import com.tictactoe.mytest.domain.model.Player

data class GameState(
    val board: Board,
    val nextPlayer: Player,
    val result: GameResult
)
