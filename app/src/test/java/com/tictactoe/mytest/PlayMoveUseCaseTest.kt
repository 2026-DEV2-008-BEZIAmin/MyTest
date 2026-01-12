package com.tictactoe.mytest

import com.tictactoe.mytest.domain.model.GameResult
import com.tictactoe.mytest.domain.model.Player
import com.tictactoe.mytest.domain.model.Position
import com.tictactoe.mytest.domain.rules.WinChecker
import com.tictactoe.mytest.domain.usecase.PlayMoveUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class PlayMoveUseCaseTest {

    private val useCase = PlayMoveUseCase(WinChecker())

    @Test
    fun `X wins with first row`() {
        var state = useCase.start()

        state = useCase.play(state, Position(0))
        state = useCase.play(state, Position(3))
        state = useCase.play(state, Position(1))
        state = useCase.play(state, Position(4))
        state = useCase.play(state, Position(2))

        assertTrue(state.result is GameResult.Win)
        assertEquals(Player.X, (state.result as GameResult.Win).winner)
    }
}