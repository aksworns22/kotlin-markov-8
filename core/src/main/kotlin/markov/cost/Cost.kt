package markov.cost

data class Cost(val value: Double) {
    companion object {
        val INITIAL = Cost(100.0)
        val DESTINATION = Cost(0.0)
        val MIN = Cost(0.0)
    }
}
