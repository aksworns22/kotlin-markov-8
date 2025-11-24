package markov.movement

data class Probability(val start: Int, val end: Int) {
    val range = start..end

    companion object {
        val ZERO = Probability(1, 0)
    }
}
