package markov.map

import markov.movement.ActionType

data class Position(val x: Int, val y: Int) {
    fun next(action: ActionType): Position {
        return when (action) {
            ActionType.UP -> Position(x, y - 1)
            ActionType.DOWN -> Position(x, y + 1)
            ActionType.LEFT -> Position(x - 1, y)
            ActionType.RIGHT -> Position(x + 1, y)
        }
    }

    companion object {
        fun of(rawPosition: String): Position {
            val position = rawPosition.split(",")
            require(position.size == 2) { "올바르지 못한 형식입니다" }
            return Position(position[0].toInt(), position[1].toInt())
        }
    }
}
