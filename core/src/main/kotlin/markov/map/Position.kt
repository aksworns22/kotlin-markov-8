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
        const val INVALID_FORMAT_ERROR_MESSAGE = "올바르지 못한 형식입니다"
        const val INVALID_VALUE_ERROR_MESSAGE = "올바르지 못한 값입니다"
        const val REQUIRED_COORDINATE_COUNT = 2
        const val COORDINATE_DELIMITER = ","
        const val X_COORDINATE = 0
        const val Y_COORDINATE = 1
        fun of(rawPosition: String): Position {
            val coordinate = rawPosition.split(COORDINATE_DELIMITER)
            require(coordinate.size == REQUIRED_COORDINATE_COUNT) { INVALID_FORMAT_ERROR_MESSAGE }
            val xCoordinate =
                coordinate[X_COORDINATE].toIntOrNull() ?: throw IllegalArgumentException(INVALID_VALUE_ERROR_MESSAGE)
            val yCoordinate =
                coordinate[Y_COORDINATE].toIntOrNull() ?: throw IllegalArgumentException(INVALID_VALUE_ERROR_MESSAGE)
            return Position(xCoordinate, yCoordinate)
        }
    }
}
