package com.tictactoe.mytest.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tictactoe.mytest.R
import com.tictactoe.mytest.domain.model.Board
import com.tictactoe.mytest.domain.model.GameResult
import com.tictactoe.mytest.domain.model.Player
import com.tictactoe.mytest.domain.model.Position
import org.koin.androidx.compose.koinViewModel

@Composable
fun TicTacToeScreen(
    modifier: Modifier = Modifier,
    viewModel: TicTacToeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            GameStatusText(
                result = state.result,
                nextPlayer = state.nextPlayer
            )

            Spacer(modifier = Modifier.height(32.dp))

            BoardGrid(
                board = state.board,
                onCellClick = viewModel::onCellClicked
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = viewModel::restart) {
                Text(stringResource(R.string.restart))
            }
        }
    }
}

@Composable
private fun GameStatusText(
    result: GameResult,
    nextPlayer: Player
) {
    Text(
        text = when (result) {
            GameResult.Ongoing ->
                stringResource(R.string.turn, nextPlayer)

            is GameResult.Win ->
                stringResource(R.string.winner, result.winner)

            GameResult.Draw ->
                stringResource(R.string.draw)
        },
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun BoardGrid(
    board: Board,
    onCellClick: (Position) -> Unit
) {
    val gridLineColor = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier.size(BoardUi.CellSize * 3)
    ) {

        GridLines(color = gridLineColor)

        BoardCells(
            board = board,
            onCellClick = onCellClick
        )
    }
}

@Composable
private fun GridLines(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val thirdWidth = size.width / 3
        val stroke = BoardUi.StrokeWidth.toPx()

        repeat(2) { index ->
            val offset = (index + 1) * thirdWidth
            drawLine(color, Offset(offset, 0f), Offset(offset, size.height), stroke)
            drawLine(color, Offset(0f, offset), Offset(size.width, offset), stroke)
        }

        drawRect(
            color = color,
            style = Stroke(width = stroke)
        )
    }
}

@Composable
private fun BoardCells(
    board: Board,
    onCellClick: (Position) -> Unit
) {
    Column {
        repeat(3) { row ->
            Row {
                repeat(3) { col ->
                    val position = Position(row * 3 + col)
                    BoardCell(
                        player = board.get(position),
                        onClick = { onCellClick(position) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BoardCell(
    player: Player?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(BoardUi.CellSize)
            .clickable(enabled = player == null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when (player) {
            Player.X -> Symbol(stringResource(R.string.x), BoardUi.XColor)
            Player.O -> Symbol(stringResource(R.string.o), BoardUi.OColor)
            null -> Unit
        }
    }
}

@Composable
private fun Symbol(
    value: String,
    color: Color
) {
    Text(
        text = value,
        fontSize = BoardUi.SymbolSize,
        fontWeight = FontWeight.Bold,
        color = color
    )
}

private object BoardUi {
    val CellSize = 100.dp
    val StrokeWidth = 5.dp
    val SymbolSize = 48.sp
    val XColor = Color(0xFF000098)
    val OColor = Color(0xFFcb0100)
}
