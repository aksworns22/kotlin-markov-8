package markov.simulation

import markov.map.Position

@JvmInline
value class Moving(val probabilities: Map<Position, Action>) {
    fun nextPosition(currentPosition: Position, probability: Int): Position {
        val actions =
            probabilities[currentPosition] ?: throw IllegalStateException("$currentPosition 에 대한 정보를 찾을 수 없습니다")
        return currentPosition.next(actions.chooseAction(probability))
    }
}
