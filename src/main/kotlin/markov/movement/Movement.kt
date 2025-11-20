package markov.movement

import markov.map.MapSize
import markov.map.Position

@JvmInline
value class Movement(val probabilities: Map<Position, Action>) {
    fun nextPosition(currentPosition: Position, probability: Int): Position {
        val actions =
            probabilities[currentPosition] ?: throw IllegalStateException("$currentPosition 에 대한 정보를 찾을 수 없습니다")
        return currentPosition.next(actions.chooseAction(probability))
    }

    fun validateWith(mapSize: MapSize) {
        val width = probabilities.keys.maxBy { it.x }.x - probabilities.keys.minBy { it.x }.x + 1
        val height = probabilities.keys.maxBy { it.y }.y - probabilities.keys.minBy { it.y }.y + 1
        require(width == mapSize.width && height == mapSize.height) { MAP_SIZE_COMPATIBLE_ERROR_MESSAGE }
    }

    companion object {
        const val MAP_SIZE_COMPATIBLE_ERROR_MESSAGE = "지도 크기와 맞지 않은 확률 정보입니다"
        fun of(rawMovements: List<String>): Movement {
            val probabilities = mutableMapOf<Position, Action>()
            for (movement in rawMovements) {
                val splitMovement = movement.split(":")
                val position = Position.of(splitMovement[0])
                val action = Action.of(splitMovement[1])
                probabilities[position] = action
            }
            return Movement(probabilities)
        }
    }
}
