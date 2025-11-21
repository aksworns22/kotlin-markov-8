package markov.output

import markov.simulation.Simulation

interface SimulationOutput {
    fun println(simulation: Simulation)
}
