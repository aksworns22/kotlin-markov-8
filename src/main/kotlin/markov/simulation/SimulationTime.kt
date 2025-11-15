package markov.simulation

import markov.input.InteractiveInput

data class SimulationTime(val time: Int) {
    override fun toString(): String {
        return time.toString()
    }

    companion object {
        fun from(input: InteractiveInput): SimulationTime {
            return SimulationTime(input.readIntegerNumber())
        }
    }
}
