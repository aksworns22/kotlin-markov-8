package markov.simulation

import markov.map.SimulationMap
import markov.output.SimulationOutput
import markov.output.SimulationResult
import markov.random.RandomGenerator

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

    companion object {
        fun startFrom(
            map: SimulationMap,
            limit: SimulationTime,
            moving: Moving,
            randomGenerator: RandomGenerator,
            output: SimulationOutput
        ): Simulation {
            val startTime = SimulationTime(0)
            var simulation = Simulation(map, startTime, limit, moving)
            while (simulation.state == SimulationState.RUNNING) {
                output.println(SimulationResult(simulation.current, simulation.map.current, simulation.state))
                simulation = simulation.next(randomGenerator.generate())
            }
            output.println(SimulationResult(simulation.current, simulation.map.current, simulation.state))
            return simulation
        }
    }
}
