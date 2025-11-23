package markov.simulation

data class SimulationTime(val time: Int) {
    init {
        require(time >= 0) { INVALID_VALUE_ERROR_MESSAGE }
    }

    override fun toString(): String {
        return time.toString()
    }

    fun next(): SimulationTime = SimulationTime(time + 1)

    companion object {
        const val INVALID_VALUE_ERROR_MESSAGE = "시간이 양수가 아닙니다"
        fun of(input: String): SimulationTime {
            val number = input.toIntOrNull() ?: throw IllegalArgumentException(INVALID_VALUE_ERROR_MESSAGE)
            return SimulationTime(number)
        }
    }
}
