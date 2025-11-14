package markov.simulation

@JvmInline
value class Action(val probabilities: Map<ActionType, Double>) {
    fun chooseAction(probability: Double): ActionType {
        var probabilitySum = 0.0
        for (action in ActionType.entries) {
            val actionProbability = probabilities[action] ?: throw IllegalStateException("$action 이 정의되지 않았습니다")
            probabilitySum += actionProbability
            if (probability < probabilitySum) {
                return action
            }
        }
        throw IllegalArgumentException("확률 값은 0이상 1미만의 실수여야만 합니다")
    }
}
