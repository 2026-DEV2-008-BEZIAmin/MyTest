package com.tictactoe.mytest.domain.rules

import com.tictactoe.mytest.domain.model.Board
import com.tictactoe.mytest.domain.model.Player
import com.tictactoe.mytest.domain.model.Position

class WinChecker {

    private val winningLines = listOf(
        listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
        listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
        listOf(0,4,8), listOf(2,4,6)
    )

    fun checkWinner(board: Board): Player? =
        winningLines.firstNotNullOfOrNull { line ->
            val p = board.get(Position(line[0]))
            if (p != null && line.all { board.get(Position(it)) == p }) p else null
        }
}
