package com.tictactoe.mytest.domain.model

data class Board(
    private val cells: List<Player?> = List(9) { null }
) {

    fun get(position: Position): Player? =
        cells[position.index]

    fun play(player: Player, position: Position): Board {
        require(get(position) == null) { "Cell already played so it's unavailable" }

        val updated = cells.toMutableList()
        updated[position.index] = player
        return Board(updated)
    }

    val isFull: Boolean
        get() = cells.all { it != null }
}
