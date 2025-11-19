package markov.simulation

data class Simulations(val log: List<Simulation>) {
    companion object {
        fun of(simulations: List<Simulation>): Simulations {
            val validLog = mutableListOf<Simulation>()
            for (simulation in simulations) {
                validLog.add(simulation)
                if (simulation.isEnd) break
            }
            return Simulations(validLog)
        }
    }
}
