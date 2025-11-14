package markov.simulation

enum class SimulationState {
    RUNNING, FAIL, SUCCESS;

    companion object {
        fun of(simulation: Simulation): SimulationState {
            if (simulation.map.current == simulation.map.destination) {
                return SUCCESS
            }
            if (simulation.current.time >= simulation.limit.time) {
                return FAIL
            }
            return RUNNING
        }
    }
}
