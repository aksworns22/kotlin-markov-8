package markov.movement

import markov.map.Position

@JvmInline
value class Movement(val probabilities: Map<Position, Action>) {
    fun nextPosition(currentPosition: Position, probability: Int): Position {
        val actions =
            probabilities[currentPosition] ?: throw IllegalStateException("$currentPosition 에 대한 정보를 찾을 수 없습니다")
        return currentPosition.next(actions.chooseAction(probability))
    }

    companion object {
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
