package markov.simulation

import markov.map.SimulationMap
import markov.movement.Movement
import markov.output.SimulationOutput
import markov.random.RandomGenerator

class SimulationController(val output: SimulationOutput) {
    fun startFrom(
        map: SimulationMap,
        limit: SimulationTime,
        movement: Movement,
        randomGenerator: RandomGenerator
    ): Simulation {
        var simulation = Simulation(map, SimulationTime(0), limit)
        output.println(simulation)
        repeat(limit.time) { time ->
            simulation = simulation.next(movement.nextPosition(simulation.map.current, randomGenerator.generate()))
            output.println(simulation)
            if (simulation.isEnd) return simulation
        }
        return simulation
    }
}
