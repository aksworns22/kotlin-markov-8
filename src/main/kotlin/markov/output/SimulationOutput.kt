package markov.output

import markov.simulation.Simulations

interface SimulationOutput {
    fun println(simulations: Simulations)
}
