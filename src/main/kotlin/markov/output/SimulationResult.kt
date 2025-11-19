package markov.output

import markov.map.Position
import markov.simulation.Simulation
import markov.simulation.SimulationState
import markov.simulation.SimulationTime

data class SimulationResult(val currentTime: SimulationTime, val currentPosition: Position, val state: SimulationState) {
    companion object {
        fun of(simulation: Simulation): SimulationResult {
            return SimulationResult(simulation.current, simulation.map.current, simulation.state)
        }
    }
}
