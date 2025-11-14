package markov.map

import markov.simulation.ActionType

data class Position(val x: Int, val y: Int) {
    fun next(action: ActionType): Position {
        return when (action) {
            ActionType.UP -> Position(x, y - 1)
            ActionType.DOWN -> Position(x, y + 1)
            ActionType.LEFT -> Position(x - 1, y)
            ActionType.RIGHT -> Position(x + 1, y)
        }
    }
}
