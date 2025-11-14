package markov.simulation

data class Probability(val start: Int, val end: Int) {
    val range = start..end

    companion object {
        val NO = Probability(1, 0)
    }
}

@JvmInline
value class Action(val probabilities: Map<ActionType, Probability>) {
    fun chooseAction(probability: Int): ActionType {
        for (action in ActionType.entries) {
            val actionProbability = probabilities[action] ?: throw IllegalStateException("$action 이 정의되지 않았습니다")
            if (probability in actionProbability.range) {
                return action
            }
        }
        throw IllegalArgumentException("확률 값은 0이상 1미만의 실수여야만 합니다")
    }
}
