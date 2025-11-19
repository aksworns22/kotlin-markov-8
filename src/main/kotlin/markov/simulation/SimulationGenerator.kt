package markov.simulation

import markov.map.SimulationMap
import markov.random.RandomGenerator

object SimulationGenerator {
    fun startFrom(
        map: SimulationMap,
        limit: SimulationTime,
        movement: Movement,
        randomGenerator: RandomGenerator
    ): List<Simulation> {
        val simulationLog = mutableListOf(Simulation(map, SimulationTime(0), limit))
        var nextMap = map
        repeat(limit.time) { time ->
            nextMap = map.next(movement.nextPosition(nextMap.current, randomGenerator.generate()))
            simulationLog.add(Simulation(nextMap, SimulationTime(time + 1), limit))
        }
        return simulationLog
    }
}
