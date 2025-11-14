package markov.simulation

import markov.map.SimulationMap

data class Simulation(
    val map: SimulationMap,
    val current: SimulationTime,
    val limit: SimulationTime,
    val moving: Moving
) {
    val state = SimulationState.of(this)
    fun next(probability: Int): Simulation {
        val nextMap = map.next(moving.nextPosition(map.current, probability))
        return Simulation(nextMap, SimulationTime(current.time + 1), limit, moving)
    }
}
