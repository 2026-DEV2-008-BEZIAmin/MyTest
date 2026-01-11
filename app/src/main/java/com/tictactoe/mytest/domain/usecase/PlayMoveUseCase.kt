package com.tictactoe.mytest.domain.usecase

import com.tictactoe.mytest.domain.model.Board
import com.tictactoe.mytest.domain.model.GameResult
import com.tictactoe.mytest.domain.model.Player
import com.tictactoe.mytest.domain.model.Position
import com.tictactoe.mytest.domain.rules.WinChecker

class PlayMoveUseCase(
    private val winChecker: WinChecker
) {

    fun start(): GameState =
        GameState(Board(), Player.X, GameResult.Ongoing)

    fun play(state: GameState, position: Position): GameState {
        require(state.result is GameResult.Ongoing) { "Game already finished" }

        val newBoard = state.board.play(state.nextPlayer, position)
        val winner = winChecker.checkWinner(newBoard)

        val result = when {
            winner != null -> GameResult.Win(winner)
            newBoard.isFull -> GameResult.Draw
            else -> GameResult.Ongoing
        }

        return state.copy(
            board = newBoard,
            nextPlayer = state.nextPlayer.other(),
            result = result
        )
    }

    private fun Player.other(): Player =
        if (this == Player.X) Player.O else Player.X
}
