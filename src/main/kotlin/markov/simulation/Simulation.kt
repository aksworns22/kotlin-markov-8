package markov.simulation

import markov.map.Position
import markov.map.SimulationMap

data class SimulationTime(val time: Int)

enum class ActionType {
    UP, DOWN, LEFT, RIGHT
}

@JvmInline
value class Action(val probabilities: Map<ActionType, Double>) {
    fun chooseAction(probability: Double): ActionType {
        var probabilitySum = 0.0
        for (action in ActionType.entries) {
            val actionProbability = probabilities[action] ?: throw IllegalStateException("$action 이 정의되지 않았습니다")
            probabilitySum += actionProbability
            if (probability < probabilitySum) {
                return action
            }
        }
        throw IllegalArgumentException("확률 값은 0이상 1미만의 실수여야만 합니다")
    }
}

@JvmInline
value class Moving(val probabilities: Map<Position, Action>) {
    fun nextPosition(currentPosition: Position, probability: Double): Position {
        val actions =
            probabilities[currentPosition] ?: throw IllegalStateException("$currentPosition 에 대한 정보를 찾을 수 없습니다")
        return currentPosition.next(actions.chooseAction(probability))
    }
}

data class Simulation(
    val map: SimulationMap,
    val current: SimulationTime,
    val limit: SimulationTime,
    val moving: Moving
) {
    fun next(probability: Double): Simulation {
        val nextMap = map.next(moving.nextPosition(map.current, probability))
        return Simulation(nextMap, SimulationTime(current.time + 1), limit, moving)
    }
}
