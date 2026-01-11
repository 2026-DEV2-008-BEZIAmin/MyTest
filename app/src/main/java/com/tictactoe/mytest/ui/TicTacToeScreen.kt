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
    viewModel: TicTacToeViewModel = koinViewModel(),
    modifier: Modifier
) {
    val state by viewModel.state.collectAsState()
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground
    val verticalSpacer = 32.dp

    Surface(
        modifier = modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = when (val result = state.result) {
                    GameResult.Ongoing -> stringResource(R.string.turn, state.nextPlayer)
                    is GameResult.Win -> stringResource(R.string.winner, result.winner)
                    GameResult.Draw -> stringResource(R.string.draw)
                },
                color = textColor,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(verticalSpacer))
            BoardGrid(
                board = state.board,
                onCellClick = viewModel::onCellClicked
            )
            Spacer(modifier = Modifier.height(verticalSpacer))
            Button(onClick = viewModel::restart) {
                Text(stringResource(R.string.restart))
            }
        }
    }
}

@Composable
fun BoardGrid(
    board: Board,
    onCellClick: (Position) -> Unit
) {
    val gridLineColor = MaterialTheme.colorScheme.onBackground
    val cellSize = 100.dp
    val strokeWidth = 4.dp
    val fontSize = 48.sp
    val fontWeight = FontWeight.Bold
    val blueColor = Color(0xFF1976D2)
    val redColor = Color(0xFFD32F2F)

    Box(
        modifier = Modifier.size(cellSize * 3)
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {
            val size = this.size
            val thirdW = size.width / 3
            val thirdH = size.height / 3

            // Vertical lines
            drawLine(
                color = gridLineColor,
                start = Offset(thirdW, 0f),
                end = Offset(thirdW, size.height),
                strokeWidth = strokeWidth.toPx()
            )
            drawLine(
                color = gridLineColor,
                start = Offset(thirdW * 2, 0f),
                end = Offset(thirdW * 2, size.height),
                strokeWidth = strokeWidth.toPx()
            )

            // Horizontal lines
            drawLine(
                color = gridLineColor,
                start = Offset(0f, thirdH),
                end = Offset(size.width, thirdH),
                strokeWidth = strokeWidth.toPx()
            )
            drawLine(
                color = gridLineColor,
                start = Offset(0f, thirdH * 2),
                end = Offset(size.width, thirdH * 2),
                strokeWidth = strokeWidth.toPx()
            )

            // Outer border
            drawRect(
                color = gridLineColor,
                style = Stroke(width = strokeWidth.toPx())
            )
        }

        // Cells
        Column {
            repeat(3) { row ->
                Row {
                    repeat(3) { col ->
                        val index = row * 3 + col
                        val cell = board.get(Position(index))

                        Box(
                            modifier = Modifier
                                .size(cellSize)
                                .clickable(enabled = cell == null) {
                                    onCellClick(Position(index))
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            when (cell) {
                                Player.X -> Text(
                                    text = stringResource(R.string.x),
                                    fontSize = fontSize,
                                    fontWeight = fontWeight,
                                    color = blueColor
                                )

                                Player.O -> Text(
                                    text = stringResource(R.string.o),
                                    fontSize = fontSize,
                                    fontWeight = fontWeight,
                                    color = redColor
                                )

                                null -> Unit
                            }
                        }
                    }
                }
            }
        }
    }
}
