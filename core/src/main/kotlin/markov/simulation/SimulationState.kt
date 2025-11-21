package markov.simulation

import markov.map.Position

enum class SimulationState(val priority: Int) {
    RUNNING(1), FAIL(2), SUCCESS(3);

    companion object {
        fun of(
            currentPosition: Position,
            destinationPosition: Position,
            currentTime: SimulationTime,
            limitTime: SimulationTime
        ): SimulationState {
            val results = mutableListOf<SimulationState>()
            if (currentPosition == destinationPosition) results.add(SUCCESS)
            if (currentTime.time >= limitTime.time) results.add(FAIL)
            return results.maxByOrNull { it.priority } ?: RUNNING
        }
    }
}
