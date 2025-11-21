package markov.simulation

import markov.input.InteractiveInput

data class SimulationTime(val time: Int) {
    override fun toString(): String {
        return time.toString()
    }

    fun next(): SimulationTime = SimulationTime(time + 1)

    companion object {
        fun from(input: InteractiveInput): SimulationTime {
            return SimulationTime(input.readIntegerNumber())
        }
    }
}
