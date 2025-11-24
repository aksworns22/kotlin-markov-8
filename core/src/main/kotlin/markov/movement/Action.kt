package markov.movement

data class Probability(val start: Int, val end: Int) {
    val range = start..end

    companion object {
        val NO = Probability(1, 0)
    }
}

@JvmInline
value class Action(val probabilities: Map<ActionType, Probability>) {
    init {
        require(probabilities.size == ActionType.entries.size) { INVALID_PROBABILITY_ERROR_MESSAGE }
        require(probabilities.values.sumOf { it.end - it.start + 1 } == TOTAL_PROBABILITY) {
            INVALID_PROBABILITY_ERROR_MESSAGE
        }
    }

    fun chooseAction(probability: Int): ActionType {
        for (action in ActionType.entries) {
            val actionProbability = probabilities[action] ?: throw IllegalStateException(INVALID_ACTION_ERROR_MESSAGE)
            if (probability in actionProbability.range) {
                return action
            }
        }
        throw IllegalArgumentException(INVALID_PROBABILITY_ERROR_MESSAGE)
    }

    companion object {
        const val TOTAL_PROBABILITY = 100
        const val INVALID_PROBABILITY_ERROR_MESSAGE = "올바른 이동 확률이 아닙니다"
        const val NEGATIVE_PROBABILITY_ERROR_MESSAGE = "확률은 음수일 수 없습니다"
        const val INVALID_ACTION_ERROR_MESSAGE = "정의되지 않은 액션입니다"
        fun of(rawActions: String): Action {
            val splitActions = rawActions.split(",")
            val probabilities = mutableMapOf<ActionType, Probability>()
            var start = 1
            for (i in 0..<splitActions.size) {
                val rawProbability = splitActions[i].toInt()
                require(rawProbability >= 0) { NEGATIVE_PROBABILITY_ERROR_MESSAGE }
                probabilities[ActionType.entries[i]] = Probability(start, start + rawProbability - 1)
                start += rawProbability
            }
            return Action(probabilities)
        }
    }
}
