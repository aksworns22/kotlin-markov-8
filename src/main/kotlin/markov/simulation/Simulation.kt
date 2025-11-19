package markov.simulation

import markov.map.SimulationMap
import markov.random.RandomGenerator

data class Simulation(
    val map: SimulationMap,
    val current: SimulationTime,
    val limit: SimulationTime,
    val moving: Moving
) {
    val state = SimulationState.of(map.current, map.destination, current, limit)
    fun next(probability: Int): Simulation {
        val nextMap = map.next(moving.nextPosition(map.current, probability))
        return Simulation(nextMap, SimulationTime(current.time + 1), limit, moving)
    }

    companion object {
        fun startFrom(
            map: SimulationMap,
            limit: SimulationTime,
            moving: Moving,
            randomGenerator: RandomGenerator
        ): List<Simulation> {
            val startTime = SimulationTime(0)
            var simulation = Simulation(map, startTime, limit, moving)
            val simulationLog = mutableListOf(simulation)
            while (simulation.state == SimulationState.RUNNING) {
                simulation = simulation.next(randomGenerator.generate())
                simulationLog.add(simulation)
            }
            return simulationLog
        }
    }
}
