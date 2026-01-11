package com.tictactoe.mytest.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tictactoe.mytest.domain.model.Board
import com.tictactoe.mytest.domain.model.GameResult
import com.tictactoe.mytest.domain.model.Position
import org.koin.androidx.compose.koinViewModel

@Composable
fun TicTacToeScreen(
    viewModel: TicTacToeViewModel = koinViewModel(),
    modifier: Modifier
) {
    val state by viewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = when (val r = state.result) {
                GameResult.Ongoing -> "Turn: ${state.nextPlayer}"
                is GameResult.Win -> "Winner: ${r.winner}"
                GameResult.Draw -> "Draw"
            }
        )
        Spacer(Modifier.height(16.dp))
        BoardGrid(state.board) { viewModel.onCellClicked(it) }
        Spacer(Modifier.height(16.dp))
        Button(onClick = viewModel::restart) {
            Text("Restart")
        }
    }
}

@Composable
private fun BoardGrid(board: Board, onClick: (Position) -> Unit) {
    Column {
        repeat(3) { row ->
            Row {
                repeat(3) { col ->
                    val index = row * 3 + col
                    val cell = board.get(Position(index))

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clickable(enabled = cell == null) {
                                onClick(Position(index))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = cell?.name ?: "")
                    }
                }
            }
        }
    }
}
