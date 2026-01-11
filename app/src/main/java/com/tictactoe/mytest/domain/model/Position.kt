package com.tictactoe.mytest.domain.model

@JvmInline
value class Position(val index: Int) {
    init {
        require(index in 0..8) { "We have 9 cells so Position must be between 0 and 8" }
    }
}
