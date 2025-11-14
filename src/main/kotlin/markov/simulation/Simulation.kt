package markov.simulation

import markov.map.SimulationMap

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
