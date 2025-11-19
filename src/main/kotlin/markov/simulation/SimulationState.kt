package markov.simulation

enum class SimulationState(val priority: Int) {
    RUNNING(1), FAIL(2), SUCCESS(3);

    companion object {
        fun of(simulation: Simulation): SimulationState {
            val results = mutableListOf<SimulationState>()
            if (simulation.map.current == simulation.map.destination) results.add(SUCCESS)
            if (simulation.current.time >= simulation.limit.time) results.add(FAIL)
            return results.maxByOrNull { it.priority } ?: RUNNING
        }
    }
}
