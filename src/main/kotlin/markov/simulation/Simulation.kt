package markov.simulation

import markov.map.Position
import markov.map.SimulationMap

data class Simulation(
    val map: SimulationMap,
    val current: SimulationTime,
    val limit: SimulationTime
) {
    val state = SimulationState.of(map.current, map.destination, current, limit)
    fun next(position: Position): Simulation {
        val nextMap = map.next(position)
        return Simulation(nextMap, SimulationTime(current.time + 1), limit)
    }
}
