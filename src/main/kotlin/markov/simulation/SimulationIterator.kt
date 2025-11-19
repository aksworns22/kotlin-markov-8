package markov.simulation

import markov.map.SimulationMap
import markov.random.RandomGenerator

object SimulationIterator {
    fun startFrom(
        map: SimulationMap,
        limit: SimulationTime,
        movement: Movement,
        randomGenerator: RandomGenerator
    ): List<SimulationMap> {
        val simulationMapLog = mutableListOf(map)
        var nextMap = map
        repeat(limit.time) {
            nextMap = map.next(movement.nextPosition(nextMap.current, randomGenerator.generate()))
            simulationMapLog.add(nextMap)
        }
        return simulationMapLog
    }
}
