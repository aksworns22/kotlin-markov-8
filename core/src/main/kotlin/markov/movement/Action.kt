package markov.movement

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
        const val START_PROBABILITY = 1
        const val TOTAL_PROBABILITY = 100
        const val INVALID_PROBABILITY_ERROR_MESSAGE = "올바른 이동 확률이 아닙니다"
        const val NEGATIVE_PROBABILITY_ERROR_MESSAGE = "확률은 음수일 수 없습니다"
        const val INVALID_ACTION_ERROR_MESSAGE = "정의되지 않은 액션입니다"
        const val PROBABILITY_DELIMITER = ","
        fun of(rawActions: String): Action {
            val splitActions = rawActions.split(PROBABILITY_DELIMITER)
            val probabilities = mutableMapOf<ActionType, Probability>()
            var start = START_PROBABILITY
            for (i in 0..<splitActions.size) {
                val rawProbability = splitActions[i].toIntOrNull() ?: throw IllegalArgumentException(
                    INVALID_PROBABILITY_ERROR_MESSAGE
                )
                require(rawProbability >= 0) { NEGATIVE_PROBABILITY_ERROR_MESSAGE }
                probabilities[ActionType.entries[i]] = Probability(start, start + rawProbability - 1)
                start += rawProbability
            }
            return Action(probabilities)
        }
    }
}
