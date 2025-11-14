package markov.output

import markov.map.Position
import markov.simulation.SimulationState
import markov.simulation.SimulationTime

data class SimulationResult(val currentTime: SimulationTime, val currentPosition: Position, val state: SimulationState)
