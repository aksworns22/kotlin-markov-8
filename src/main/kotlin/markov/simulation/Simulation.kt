package markov.simulation

import markov.map.SimulationMap

data class Simulation(
    val map: SimulationMap,
    val current: SimulationTime,
    val limit: SimulationTime
) {
    val state = SimulationState.of(map.current, map.destination, current, limit)
    val isEnd = (state == SimulationState.FAIL) || (state == SimulationState.SUCCESS)
    val isSuccess = state == SimulationState.SUCCESS
    val isFail = state == SimulationState.FAIL
}
